package at.rocklogic.kafka;



public enum KafkaTopics {

    ClientUser(null);


    private final Class<?> dtoClass;

    KafkaTopics(Class<?> dtoClass) {

        this.dtoClass = dtoClass;
    }

    public Class<?> getDtoClass() {

        return dtoClass;
    }

}
