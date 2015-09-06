package chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories
@Controller
@RequestMapping("/")
public class Application {
    static Logger logger = LoggerFactory.getLogger(Application.class);


	@RequestMapping("")
	public String index(Model model) {
		
		return "index";
	}
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
