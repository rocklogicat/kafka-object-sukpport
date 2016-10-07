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

    public static final String TOPIC_CONFIGURATION = "object.deserialization.topics";
    private Map<String, KafkaTopics> topics;

    private static ObjectMapper objectMapper;

    static {
        // FIXME: this should be done by Spring
        objectMapper = new JsonConfiguration().objectMapper();

    }

    /**
     * The configure method simply receives the entire Property instance you used to configure the Producer/Consumer.
     * @param map
     * @param b
     */
    public void configure(Map<String, ?> map, boolean b) {
        topics = (Map)map.get(TOPIC_CONFIGURATION);
        if(topics == null){
            throw new RuntimeException("Topic Configuration not found");
        }
    }

    /**
     * Deserializes the incoming message of a topic, by using the topic-object Mapping provided in the
     * Configuration
     * @param topic the topic in which the bytes have been sent
     * @param bytes payload of the Message
     * @return deserialized Object T
     */
    public T deserialize(String topic, byte[] bytes) {
        StopWatch stopWatch = new StopWatch("deserialize");
        stopWatch.start("kafka topic");
        KafkaTopics kafkaTopic = topics.get(topic);
        stopWatch.stop();
        T dataObject;
        if(kafkaTopic != null){

        String json = new String(bytes);
        stopWatch.start("parsing json");
        dataObject = parseJson(kafkaTopic, json);
        stopWatch.stop();

        log.debug(stopWatch.prettyPrint());

        }
        else{
            log.error("Could not retrieve topic \""+topic+"\" from topic map");
            dataObject = null;
        }
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
