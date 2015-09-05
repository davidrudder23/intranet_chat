package chat.authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authentication")
public class AuthenticationResource {

	/*
	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}
	
	@RequestMapping("/postLogin")
	public String postLogin(String username, String password, Model model) {
		return "login";
	}*/
}
