package at.rocklogic.kafka.deserializer;


import at.rocklogic.kafka.TopicMapping;
import at.rocklogic.kafka.configuration.JsonConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.reflections.Reflections;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Generic ObjectDeserializer, deserializes Objects of class T
 * from a json String. Used like any other Deserializer
 * @param <T> The class, the json String has to be deserialized
 */
@Slf4j
public class ObjectDeserializer<T> implements Deserializer<T> {

    private Set<Class<?>> topicClasses;

    private static ObjectMapper objectMapper;

    static {
        // FIXME: this should be done by Spring
        objectMapper = new JsonConfiguration().objectMapper();

    }

    /**
     * initializes the Deserializer, therefore all occurences
     * of @ToppicMapping annotated classes are taken.
     */
    public ObjectDeserializer(){
        //TODO narrow focus of package down?
        Reflections reflections = new Reflections("");
        topicClasses = reflections.getTypesAnnotatedWith(TopicMapping.class);

    }

    /**
     * does nothing in this case
     * @param map map for configuration
     * @param b boolean
     */
    public void configure(Map<String, ?> map, boolean b) {
        // do nothing
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

        stopWatch.stop();
        T dataObject;

        String json = new String(bytes);
        stopWatch.start("parsing json");
        dataObject = parseJson(getClassForTopic(topic), json);
        stopWatch.stop();

        log.debug(stopWatch.prettyPrint());

        return dataObject;
    }

    private Class<?> getClassForTopic(String topic){
        Class<?> topicClass = null;
        for(Class<?> entityClass : topicClasses){
            TopicMapping annotation = entityClass.getAnnotation(TopicMapping.class);
            boolean mappingMatches = false;
            if(!StringUtils.isEmpty(annotation.topicPattern())){
                mappingMatches = topic.matches(annotation.topicPattern());
            }
            else{
                mappingMatches = topic.equals(annotation.topic());
            }
            if(mappingMatches){
                if(topicClass == null){
                    topicClass = entityClass;
                }
                else{
                    throw new RuntimeException("Multiple Classes found for topic " + topic+". Should only be one Class per topic.");
                }
            }
        }
        if(topicClass == null){
            throw new RuntimeException("No Class found for topic " + topic+".");

        }
        return topicClass;

    }

    
    private T parseJson(Class<?> topicClass, String json) {
        T dataObject;
        try {
            dataObject = (T) objectMapper.readValue(json, topicClass);
        } catch (IOException e) {
            log.error("An error occurred while parsing the payload: ", e);
            dataObject = null;
        }
        return dataObject;
    }

    /**
     * does nothing in this case
     */
    public void close() {
        // do nothing
    }
}
