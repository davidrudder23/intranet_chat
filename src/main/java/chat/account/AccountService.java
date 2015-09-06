package chat.account;


public interface AccountService {

	public Account addAccount(String username, String plaintextPassword);
	
	public void logLogin(Account account, String loginAddress);
}
