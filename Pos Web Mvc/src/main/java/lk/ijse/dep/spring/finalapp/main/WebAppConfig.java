package lk.ijse.dep.spring.finalapp.main;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan("lk.ijse.dep.spring.finalapp")
@Configuration
@EnableWebMvc
public class WebAppConfig {
}
