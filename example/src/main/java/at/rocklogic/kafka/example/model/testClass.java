package at.rocklogic.kafka.example.model;

import at.rocklogic.kafka.TopicMapping;
import lombok.Data;

@Data
@TopicMapping(topic = "test")
public class testClass extends baseClass {

    private String name;

    private String foo;

    private int bar;
}
