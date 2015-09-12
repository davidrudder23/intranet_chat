package chat.upload;

import java.io.File;
import java.net.URLConnection;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.beans.factory.annotation.Autowired;

import chat.message.Message;

public class UploadServiceImpl implements UploadService {
	@Autowired
	UploadRepository uploadRepository;
	
	public Upload uploadFile(Message message, File file) {
		Upload upload = new Upload();
		
		uploadToS3("upload-"+message.getId()+"-original", file);
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
		try {
			String awsAccessKey = "YOUR_AWS_ACCESS_KEY";
			String awsSecretKey = "YOUR_AWS_SECRET_KEY";
			AWSCredentials awsCredentials = 
			    new AWSCredentials(awsAccessKey, awsSecretKey);
			
			S3Service s3Service = new RestS3Service(awsCredentials);
	
			S3Object object = new S3Object(name);
			object.setContentType(URLConnection.guessContentTypeFromName(file.getName()));
			S3Bucket bucket;
				bucket = s3Service.getBucket("chat-uploads");
			if (bucket == null) {
				bucket = s3Service.createBucket(bucket);
			}
			s3Service.putObject(bucket, object)
		} catch (S3ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ulr;
	}
}
