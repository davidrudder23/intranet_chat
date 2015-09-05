package chat.message;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long>{
	
	public List<Message> findByDateGreaterThan(Date date);

}
