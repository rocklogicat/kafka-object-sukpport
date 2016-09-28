package at.rocklogic.kafka.example.consumer;

import at.rocklogic.kafka.example.configuration.KafkaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(KafkaConfiguration.class)
public class Application {

    /**
     * Run this by first "mvn clean" and then run the Application.main
     *
     * @param args
     */
    public static void main(final String[] args) {

        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);

    }
}
