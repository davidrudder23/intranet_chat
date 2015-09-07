package chat.message;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import chat.account.Account;
import chat.room.Room;

public interface MessageRepository extends CrudRepository<Message, Long>{
	
	public List<Message> findByRoomAndDateGreaterThan(Room room, Date date);

	public Message findFirst1ByAccountOrderByDateDesc(Account account);

	public int countByAccount(Account account);
}