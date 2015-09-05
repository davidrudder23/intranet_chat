package chat.account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@Entity
public class Account {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
    String username;
    
    public Account(String username) {
    	this.username = username;
    }

    public Account() {
    	this.username = "";
    }

    public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}
    
    
    
}
