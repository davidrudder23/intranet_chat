package chat.account;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AccountRepository accountRepository;
	
	public Account addAccount(String username, String plaintextPassword) {
	
		String hashedPassword = passwordEncoder.encode(plaintextPassword);
		Account account = new Account(username, hashedPassword);
		account = accountRepository.save(account);
		return account;
	}
	
	public void logLogin(Account account, String loginAddress) {
		account.lastLogin = new Date();
		account.lastLoginAddress = loginAddress;
		accountRepository.save(account);
	}

}
