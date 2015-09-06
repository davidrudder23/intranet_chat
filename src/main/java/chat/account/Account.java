package chat.account;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@Entity
public class Account {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
    String username;
    
    String password;
    
    Date lastLogin;
    
    String lastLoginAddress;
    
    Account(String username, String hashedPassword) {
    	this.username = username;
    	this.password = hashedPassword;
    }

    Account() {
    	this.username = "";
    }

    public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}
    
    
    
}
