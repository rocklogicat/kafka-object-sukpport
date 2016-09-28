package at.rocklogic.kafka.example.producer.service;

import at.rocklogic.kafka.example.model.baseClass;
import at.rocklogic.kafka.example.model.testClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageProducer {

    @Autowired
    private KafkaTemplate<Integer, baseClass> kafkaTemplate;

    @Scheduled(fixedDelay = 1000)
    public void sendMessage() {

            testClass test = new testClass();
            test.setBar(2981);
            test.setFoo("foobar");
            test.setName("barfoo");
            kafkaTemplate.send("test", test);
            log.info("sent message");
    }
}
