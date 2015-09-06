package chat.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/room")
public class RoomResource {
	
	@Autowired
	RoomRepository roomRepository;
	
	@RequestMapping("/getAll")
	public @ResponseBody Iterable<Room> getAllRooms() {
		Iterable<Room> rooms = roomRepository.findAll();
		
		return rooms;
	}
}
