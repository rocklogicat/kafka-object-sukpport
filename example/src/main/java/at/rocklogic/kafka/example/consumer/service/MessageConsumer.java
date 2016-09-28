package at.rocklogic.kafka.example.consumer.service;


import at.rocklogic.kafka.example.model.baseClass;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageConsumer {

    @KafkaListener(topics = {"test"})
    @Synchronized
    public void receiveMessage(ConsumerRecord<String, baseClass> data) {

        log.info("Receiving data...");

        baseClass dataValue = data.value();
        log.info("Received " + dataValue.getClass().getName() +" Object: " + dataValue);

    }
}
