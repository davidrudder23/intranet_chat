package chat.room;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;
    
    String name;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
    
    

}
