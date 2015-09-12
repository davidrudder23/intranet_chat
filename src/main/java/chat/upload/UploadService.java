package chat.upload;

import java.io.File;

import chat.message.Message;

public interface UploadService {

	public Upload uploadFile(Message message, File file);
}
