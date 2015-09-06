package chat.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import chat.account.Account;
import chat.account.AccountRepository;
import chat.account.AccountService;

@Service
public class ChatLoginHandler implements AuthenticationSuccessHandler {

	@Autowired
	AccountService accountService;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String username = authentication.getName();
	
		Account account = accountRepository.findByUsername(username);
		
		accountService.logLogin(account, request.getRemoteAddr());
		
		response.sendRedirect("/");

	}

}
