# kafka-object-support 

Generic Objectserialization and -deserialization for Kafka

Tested with Kafka 0.8.2.1, 0.9.0.1 and 0.10.0.1.

## Usage:
Build the object support source and add it to your build automation system.
Set up a Consumer or Producer and set in the Configuration for the ContainerFactory.

### For Producer:
Set 
```ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG ``` as well as ``` ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG ``` to ```at.rocklogic.kafka.serializer.ObjectSerializer.class```

### For Consumer:

```ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG ``` as well as ``` ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG ``` to ```at.rocklogic.kafka.serializer.ObjectDeserializer.class```.

Specify your classes, which should be mapped per topic with the ``` @TopicMapping ```