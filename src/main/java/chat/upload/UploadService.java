package chat.upload;

import org.springframework.web.multipart.MultipartFile;

import chat.message.Message;

public interface UploadService {

	public Upload uploadFile(Message message, MultipartFile file) throws UploadException;
}
