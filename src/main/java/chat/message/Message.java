package chat.message;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Configurable;

import chat.account.Account;

@Configurable
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
    @ManyToOne
    Account account;
    
    String message;
    
    Date date;
    
    public Message () {
    	this.account = null;
    	this.message = "";
    	this.date = new Date();
    }

    public Message (Account account, String message) {
    	this.account = account;
    	this.message = message;
    	this.date = new Date();
    }

	public long getId() {
		return id;
	}

	public Account getAccount() {
		return account;
	}

	public String getMessage() {
		return message;
	}

	public Date getDate() {
		return date;
	}
    
    
}
