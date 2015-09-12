package chat.message;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import chat.account.Account;
import chat.account.AccountRepository;
import chat.room.Room;
import chat.upload.UploadService;
import chat.util.HttpUtils;

@Controller
@RequestMapping("/message")
public class MessageResource {
    static Logger logger = LoggerFactory.getLogger(MessageResource.class);
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	UploadService uploadService;

	@RequestMapping("/submitMessage")
	public @ResponseBody Map<String, Object> submitMessage(Room room, String message, @RequestParam(value="file", required = false) MultipartFile file, Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Account account = accountRepository.findByUsername(username);
		
		if (account == null) {
			// This shouldn't happen because we need an account to pass authentication
			return HttpUtils.getFailureStatus("Authentication Failed");
		}
		
		Message messageObject = new Message(account, room, message);
		messageRepository.save(messageObject);
		
		if ((file != null) && (!file.isEmpty())) {
			try {
				File s3File = new File("");
				file.transferTo(s3File);
				uploadService.uploadFile(messageObject, s3File);
			} catch (IllegalStateException | IOException e) {
				return HttpUtils.getFailureStatus("File Transfer Failed");
				Map<String, Object> status = new HashMap<String, Object>();
			}
		}
		
		Map<String, Object> status = new HashMap<String, Object>();
		status.put ("status", "Success");
		status.put ("message", "Message Submitted");
		status.put ("date", messageObject.getDate());
		return status;
	}
	
	@RequestMapping("/getMessages/{room}")
	public @ResponseBody List<Message> getMessages(@PathVariable("room") Room room) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -30);
		
		List<Message> messages = messageRepository.findByRoomAndDateGreaterThanOrderByDateDesc(room, calendar.getTime());
		return messages;
	}
	
	@RequestMapping("/getLatestPerAccount")
	public @ResponseBody List<Message> getLatestPerAccount() {
		Iterable<Account> accounts = accountRepository.findAll();
		List<Message> messages = StreamSupport.stream(accounts.spliterator(), false)
				.filter(a->(messageRepository.countByAccount(a)>0))
				.map(a->messageRepository.findFirst1ByAccountOrderByDateDesc(a))
				.sorted((a,b)->b.getDate().compareTo(a.getDate()))
				.collect(Collectors.toList());
		return messages;
	}
}
