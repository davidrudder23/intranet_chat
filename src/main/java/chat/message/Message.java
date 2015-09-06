package chat.message;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Configurable;

import chat.account.Account;
import chat.room.Room;

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
    
    @ManyToOne
    Room room;
    
    public Message () {
    	this.account = null;
    	this.room = null;
    	this.message = "";
    	this.date = new Date();
    }

    public Message (Account account, Room room, String message) {
    	this.account = account;
    	this.room = room;
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
   
	public Room getRoom() {
		return room;
	}
    
}
