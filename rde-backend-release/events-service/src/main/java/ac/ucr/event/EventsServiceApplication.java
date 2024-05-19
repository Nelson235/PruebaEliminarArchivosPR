package ac.ucr.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EventsServiceApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(EventsServiceApplication.class);
        application.setAddCommandLineProperties(false);
        application.run(args);
    }

}
