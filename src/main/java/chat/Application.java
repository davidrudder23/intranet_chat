package chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories
@Controller
@RequestMapping("/")
public class Application {

	@RequestMapping("index")
	public String index(Model model) {
		
		return "index";
	}
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
