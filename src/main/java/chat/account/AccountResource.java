package chat.account;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import chat.message.Message;
import chat.message.MessageRepository;

@Configurable
@Controller 
@RequestMapping("/account")
public class AccountResource {

	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	AccountRepository accountRepository;
	

}
