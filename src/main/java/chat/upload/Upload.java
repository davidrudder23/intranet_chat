package chat.upload;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Configurable;

import chat.message.Message;

@Configurable
@Entity
public class Upload {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
    @ManyToOne
    Message message;
    
    String originalUrl;
    
    String thumbnailUrl;

	public long getId() {
		return id;
	}

	public Message getMessage() {
		return message;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

    
    
}
