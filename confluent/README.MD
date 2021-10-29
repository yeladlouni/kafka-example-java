Confluent installation and running steps:

1. Download Confluent 6.2.1 by choosing manual installation https://docs.confluent.io/platform/current/installation/installing_cp/zip-tar.html
2. Unzip the confluent-6.2.1.zip file
3. Launch Zookeeper using the following command: ./bin/zookeeper-server-start ./etc/kafka/zookeeper.properties &
4. Launch Kafka Broker using the following command: ./bin/kafka-server-start ./etc/kafka/server.properties &
5. Launch Schema Registry using the following command ./bin/schema-registry-start ./etc/schema-registry/schema-registry.properties &
6. Launch REST Proxy using the following command: ./bin/kafka-rest-start ./etc/kafka-rest/kafka-rest.properties &
7. Optional: Launch the Control Center: ./bin/control-center-start ./etc/confluent-control-center/control-center-minimal.properties &

NB: We should use Java <= 11

Once all these components are launched, open the Control Center UI http://localhost:9021/

To produce and consume messages using the REST Proxy:

1. Create a topic using the REST call: 

curl -X POST -H "Content-Type: application/vnd.kafka.avro.v2+json" \
   -H "Accept: application/vnd.kafka.v2+json" \
   --data '{"value_schema": "{\"type\": \"record\", \"name\": \"User\", \"fields\": [{\"name\": \"name\", \"type\": \"string\"}]}", "records": [{"value": {"name": "testUser"}}]}' \
   "http://localhost:8082/topics/avrotest3"

2. Produce a message on this topic

curl -X POST -H "Content-Type: application/vnd.kafka.avro.v2+json" \
-H "Accept: application/vnd.kafka.v2+json" \
--data '{"key_schema": "{\"name\":\"user_id\"  ,\"type\": \"int\"   }", "value_schema": "{\"type\": \"record\", \"name\": \"User\", \"fields\": [{\"name\": \"name\", \"type\": \"string\"}]}", "records": [{"key" : 1 , "value": {"name": "testUser"}}]}' \
"http://localhost:8082/topics/avrokeytest3"

3. Create a consumer 

curl -X POST  -H "Content-Type: application/vnd.kafka.v2+json" \
--data '{"name": "my_consumer_instance", "format": "avro", "auto.offset.reset": "earliest"}' \
http://localhost:8082/consumers/my_avro_consumer

4. Subscribe to a topic

curl -X POST -H "Content-Type: application/vnd.kafka.v2+json" --data '{"topics":["avrotest"]}' \
http://localhost:8082/consumers/my_avro_consumer/instances/my_consumer_instance/subscription

5. List records

curl -X GET -H "Accept: application/vnd.kafka.avro.v2+json" \
http://localhost:8082/consumers/my_avro_consumer/instances/my_consumer_instance/records

6. Delete a consumer

curl -X DELETE -H "Content-Type: application/vnd.kafka.v2+json" \
http://localhost:8082/consumers/my_avro_consumer/instances/my_consumer_instance



