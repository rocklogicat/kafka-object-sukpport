package at.rocklogic.kafka.serializer;


import at.rocklogic.kafka.configuration.JsonConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.util.StopWatch;

import java.util.Map;

@Slf4j
public class ObjectSerializer<T> implements Serializer<T>{

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
// do nothing
    }

    public byte[] serialize(String topic, T dataObject) {
        StopWatch stopWatch = new StopWatch("serialize");

        stopWatch.start("json generation");
        String json = buildJson(dataObject);
        stopWatch.stop();

        log.info("json constructed: "+json);

        stopWatch.start("get bytes of json string");
        byte[] encrypted = json.getBytes();
        stopWatch.stop();

        log.debug(stopWatch.prettyPrint());
        return encrypted;

    }

    public void close() {
        // do nothing
    }

    private String buildJson(T dataObject) {
        try {
            return objectMapper.writeValueAsString(dataObject);
        } catch (JsonProcessingException e) {
            log.error("error occurred while generating json");
            throw new RuntimeException(e);
        }
    }
}
