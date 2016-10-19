package at.rocklogic.kafka.serializer;


import at.rocklogic.kafka.configuration.JsonConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.util.StopWatch;

import java.util.Map;

/**
 * Generic ObjectSerializer, serializes Objects of class T
 * to a json String. Used like any other Serializer
 * @param <T> The class to be serialized
 */
@Slf4j
public class ObjectSerializer<T> implements Serializer<T>{

    private static ObjectMapper objectMapper;

    static {
        // FIXME: this should be done by Spring
        objectMapper = new JsonConfiguration().objectMapper();
    }


    /**
     * does nothing in this case
     * @param map map for configuration
     * @param b boolean
     */
    public void configure(Map<String, ?> map, boolean b) {
    }

    /**
     * serializes the given dataObject to a JSON String
     * @param topic the topic, the object is sent to; not used here
     * @param dataObject the object which should be serialized
     * @return JSON Byte Array
     */
    public byte[] serialize(String topic, T dataObject) {
        StopWatch stopWatch = new StopWatch("serialize");

        stopWatch.start("json generation");
        String json = buildJson(dataObject);
        stopWatch.stop();

        log.info("json constructed: "+json);

        stopWatch.start("get bytes of json string");
        byte[] encrypted = json.getBytes();
        stopWatch.stop();

        //TODO variablen umbenennen

        log.debug(stopWatch.prettyPrint());
        return encrypted;

    }

    /**
     * does nothing in this case
     */
    public void close() {
    }

    /**
     * builds the JSON String out of a dataObject T
     * @param dataObject the object which will be serialized
     * @return
     */
    private String buildJson(T dataObject) {
        try {
            return objectMapper.writeValueAsString(dataObject);
        } catch (JsonProcessingException e) {
            log.error("error occurred while generating json");
            throw new RuntimeException(e);
        }
    }
}
