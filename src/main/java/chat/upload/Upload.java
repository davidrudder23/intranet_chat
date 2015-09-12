package chat.upload;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Configurable;

import chat.message.Message;

@Configurable
@Entity
public class Upload {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
    Message message;
    
    String originalUrl;
    
    String thumbnaulUrl;
    
    
}
