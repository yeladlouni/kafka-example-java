Steps to connect Kafka to Cassandra as a sink:

1. Download the Datastax Kafka Driver from the URL:

https://docs.datastax.com/en/kafka/doc/kafka/install/kafkaInstall.html

2. Unzip kafka-connect-cassandra-sink-1.4.0.tar.gz

3. Create a folder on your home directory "connectors for example" and put the jar inside

4. Create the properties file for the standalone connector cassandra-sink-standalone.properties

5. Change the worker properties file "connect-standalone.properties" found inside the config directory of Kafka

6. Launch the following command

bin/connect-standalone.sh /Users/yassine/kafka/kafka-example-java/connect/connect-standalone.properties /Users/yassine/kafka/kafka-example-java/connect/cassandra-sink-standalone.properties

