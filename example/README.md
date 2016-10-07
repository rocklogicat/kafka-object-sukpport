# Example for generic object-support for kafka. 

Tested with apache kafka server versions 0.9.0.1 and 0.10.0.1.

> NOTE: Does not work with kafka servers with versions prior to 0.9.x

## Usage

Start your zookeeper on ```localhost:9092``` and the kafka server on ``` localhost:2181 ```.

Start the Consumer by launching ``` at.rocklogic.kafka.example.consumer.Application.main```.

Start the Producer by launching ``` at.rocklogic.kafka.example.producer.Application.main```.

The consumer should display the received Object.