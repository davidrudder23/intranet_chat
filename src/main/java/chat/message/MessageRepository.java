package chat.message;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import chat.room.Room;

public interface MessageRepository extends CrudRepository<Message, Long>{
	
	public List<Message> findByRoomAndDateGreaterThan(Room room, Date date);

}
