package at.rocklogic.kafka.deserializer;


import at.rocklogic.kafka.KafkaTopics;
import at.rocklogic.kafka.configuration.JsonConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class ObjectDeserializer<T> implements Deserializer<T> {

    private static ObjectMapper objectMapper;

    static {
        // FIXME: this should be done by Spring
        objectMapper = new JsonConfiguration().objectMapper();
    }

    public void configure(Map<String, ?> map, boolean b) {
        // do nothing
    }

    public T deserialize(String topic, byte[] bytes) {
        StopWatch stopWatch = new StopWatch("deserialize");
        stopWatch.start("kafka topic");
        KafkaTopics kafkaTopic = KafkaTopics.valueOf(topic);
        stopWatch.stop();

        String json = new String(bytes);
        stopWatch.start("parsing json");
        T dataObject = parseJson(kafkaTopic, json);
        stopWatch.stop();

        log.debug(stopWatch.prettyPrint());

        return dataObject;
    }

    private T parseJson(KafkaTopics kafkaTopic, String json) {
        T dataObject;
        try {
            dataObject = (T) objectMapper.readValue(json, kafkaTopic.getDtoClass());
        } catch (IOException e) {
            log.error("", e);
            dataObject = null;
        }
        return dataObject;
    }

    public void close() {
        // do nothing
    }
}
