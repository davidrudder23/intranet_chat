package chat.upload;

import org.springframework.data.repository.CrudRepository;

public interface UploadRepository extends CrudRepository<Upload, Long>{
	
	public Upload findByUsername(String username);
	
}
