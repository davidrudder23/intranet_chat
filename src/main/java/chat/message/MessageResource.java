package chat.message;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import chat.account.Account;
import chat.account.AccountRepository;

@Controller
@RequestMapping("/message")
public class MessageResource {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	MessageRepository messageRepository;

	@RequestMapping("/submitMessage")
	public @ResponseBody Map<String, Object> submitMessage(String message, Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Account account = accountRepository.findByUsername(username);
		
		if (account == null) {
			// This shouldn't happen because we need an account to pass authentication
			Map<String, Object> status = new HashMap<String, Object>();
			status.put ("status", "Failure");
			status.put ("message", "Authentication failed");
			status.put ("date", new Date());
			return status;
		}
		
		Message messageObject = new Message(account, message);
		messageRepository.save(messageObject);
		
		Map<String, Object> status = new HashMap<String, Object>();
		status.put ("status", "Success");
		status.put ("message", "Message Submitted");
		status.put ("date", messageObject.getDate());
		return status;
	}
	
	@RequestMapping("/getMessages")
	public @ResponseBody List<Message> getMessages() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -30);
		
		List<Message> messages = messageRepository.findByDateGreaterThan(calendar.getTime());
		return messages;
	}
}
