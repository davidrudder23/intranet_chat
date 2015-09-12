package chat.upload;

import java.io.File;
import java.net.URLConnection;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
	
	
	public Upload uploadFile(Message message, File file) {
		Upload upload = new Upload();
		
		String url = uploadToS3("upload-"+message.getId()+"-original", file);
		
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
	String uploadToS3(String name, File file) {
		String url = null;
		
		logger.debug("AWS Access Key = "+awsAccessKey);
		try {
			AWSCredentials awsCredentials = new AWSCredentials(awsAccessKey, awsSecretKey);
			
			S3Service s3Service = new RestS3Service(awsCredentials);
	
			S3Object object = new S3Object(name);
			object.setContentType(URLConnection.guessContentTypeFromName(file.getName()));
			S3Bucket bucket;
				bucket = s3Service.getBucket("chat-uploads");
			if (bucket == null) {
				bucket = s3Service.createBucket(bucket);
			}
			s3Service.putObject(bucket, object);
		} catch (S3ServiceException e) {
			logger.error("Could not upload to S3", e);
		}
		
		return url;
	}
}
