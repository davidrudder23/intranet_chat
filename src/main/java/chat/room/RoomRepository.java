package chat.room;

import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long>{
	
	public Room findByName(String name);

}
