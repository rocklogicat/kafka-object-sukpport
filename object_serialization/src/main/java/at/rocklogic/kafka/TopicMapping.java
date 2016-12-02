package at.rocklogic.kafka;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TopicMapping annotation
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TopicMapping {

    /**
     * The Topic which this class is mapped to; will be ignored
     * if a topicPattern is given.
     * @return the mapped topic
     */
    String topic() default "";

    /**
     * A regex TopicPattern.
     * All Topics matching this pattern will be mapped to this class.
     * @return the topicPattern
     */
    String topicPattern() default "";
}
