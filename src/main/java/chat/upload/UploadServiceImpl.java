package chat.upload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.acl.GroupGrantee;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import chat.message.Message;
import chat.message.MessageResource;

@Service
public class UploadServiceImpl implements UploadService {
    static Logger logger = LoggerFactory.getLogger(MessageResource.class);
    
	@Autowired
	UploadRepository uploadRepository;

	@Value("${aws.access.key}")
	private String awsAccessKey;
	
	@Value("${aws.secret.key}")
	private String awsSecretKey;
	
	@Value("${aws.bucket.name}")
    String bucketName;

	public Upload uploadFile(Message message, MultipartFile file) throws UploadException {
		Upload upload = new Upload();
		
		String url = uploadToS3("upload-"+message.getId()+"-original", file);
		logger.debug("Uploaded url="+url);
		
		upload.message = message;
		upload.originalUrl = url;
		uploadRepository.save(upload);
		
		return upload;
	}
	
	/**
	 * 
	 * @param name
	 * @param file
	 * @return the URL of the uploaded file
	 */
	String uploadToS3(String name, MultipartFile file) throws UploadException {
		String url = null;
		
		AWSCredentials awsCredentials = new AWSCredentials(awsAccessKey, awsSecretKey);
		
		S3Service s3Service = null;
		try {
			s3Service = new RestS3Service(awsCredentials);
		} catch (S3ServiceException e) {
			logger.error("Could not login to S3", e);
			return null;
		}
		

		S3Object s3Object = new S3Object(name);
		String contentType = file.getContentType();
		logger.error("Content-type: "+contentType+" from name="+file.getName());
		s3Object.setContentType(contentType);
		try {
			s3Object.setDataInputStream(file.getInputStream());
		} catch (Exception e) {
			logger.error("Could not set data input stream", e);
			throw new UploadException("Could not upload file", e);
		}
		
		S3Bucket bucket = null;
		logger.error("Using bucket "+bucketName);
		try {
			bucket = s3Service.getBucket(bucketName);
		} catch (S3ServiceException e) {
			logger.error("Could not get S3 bucket", e);
		}
		
		if (bucket == null) {
			try {
				bucket = s3Service.createBucket(bucketName);
			} catch (S3ServiceException e) {
				logger.error("Could not create S3 bucket", e);
				return null;
			}
		}

		try {
			s3Service.putObject(bucket, s3Object);
			
			AccessControlList objAcl = s3Service.getObjectAcl(bucket, name);
			objAcl.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_READ);
			s3Object.setAcl(objAcl);
			s3Service.putObjectAcl(bucket, s3Object);
			
			url = s3Service.createUnsignedObjectUrl(bucket.getName(), name, false, true, false);
			logger.error("Got url from AWS - "+url);
		} catch (S3ServiceException e) {
			logger.error("Could not create S3 bucket", e);
			return null;
		}

		return url;
	}
}
