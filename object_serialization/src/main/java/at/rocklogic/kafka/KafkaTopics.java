package at.rocklogic.kafka;



public class KafkaTopics {

    private Class dtoClass;
    private String topic;

    public KafkaTopics(String topic, Class dtoClass){
        this.dtoClass = dtoClass;
        this.topic = topic;
    }

    public Class<?> getDtoClass(){
        return dtoClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KafkaTopics that = (KafkaTopics) o;

        return topic != null ? topic.equals(that.topic) : that.topic == null;

    }

    @Override
    public int hashCode() {
        return topic != null ? topic.hashCode() : 0;
    }
}
