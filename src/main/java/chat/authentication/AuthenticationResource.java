package chat.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import chat.account.Account;
import chat.account.AccountRepository;
import chat.account.AccountService;

@Controller
@RequestMapping("/")
public class AuthenticationResource {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountService accountService;
	
	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}
	
	@RequestMapping("/registration")
	public String registration(Model model) {
		return "registration";
	}
	
	@RequestMapping("/register")
	public String register(String username, String password, Model model) {
		Account account = accountRepository.findByUsername(username);
		if (account != null) {
			
			return "registration";
		}
		
		account = accountService.addAccount(username, password);
		
		return "redirect:/";
	}
}
