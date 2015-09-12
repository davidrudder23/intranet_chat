package chat.upload;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import chat.account.Account;
import chat.account.AccountRepository;
import chat.message.Message;
import chat.message.MessageRepository;
import chat.room.Room;

@Configurable
@Controller
@RequestMapping("/upload")
public class UploadResource {

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	UploadRepository uploadRepository;
	
	@Autowired
	UploadService uploadService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> handleFileUpload(String message, MultipartFile file, Room room) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Account account = accountRepository.findByUsername(username);
		
		if (file.isEmpty()) {
			Map<String, Object> status = new HashMap<String, Object>();
			status.put ("status", "Failured");
			status.put ("message", "No file provided");
			status.put ("date", new Date());
			return status;
		}
		
		String originalFilename = file.getOriginalFilename();
		File s3File = new File(originalFilename);
		try {
			file.transferTo(s3File);
		} catch (IllegalStateException | IOException e) {
			Map<String, Object> status = new HashMap<String, Object>();
			status.put ("status", "Failured");
			status.put ("message", "Invalid file");
			status.put ("date", new Date());
			return status;
		}
		
		Message messageObject = new Message(account, room, message);
		messageRepository.save(messageObject);
		
		uploadService.uploadFile(messageObject, s3File);
		
		Map<String, Object> status = new HashMap<String, Object>();
		status.put ("status", "Success");
		status.put ("message", "File Uploaded");
		status.put ("date", messageObject.getDate());
		return status;
	}

}
