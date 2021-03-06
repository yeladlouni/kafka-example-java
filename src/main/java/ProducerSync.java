import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class ProducerSync {
    public static void main(String[] args) throws Exception {
        System.out.println("**** Starting Basic Producer ****");

        Properties settings = new Properties();
        settings.put("client.id", "basic-producter-v0.0.1");
        settings.put("bootstrap.servers", "localhost:9092");
        settings.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        settings.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        final KafkaProducer<String, String> producer = new KafkaProducer<String, String>(settings);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("**** Stopping Basic Producer ****");
            producer.close();
        }));

        final String topic = "hello_world_topic";

        for (int i = 1; i <= 1000000; i++) {
            final String key = "key-" + i;
            final String value = "value-" + i;
            final ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
            RecordMetadata metadata = producer.send(record).get();
            System.out.println(metadata.topic());
            System.out.println(metadata.partition());
            System.out.println(metadata.timestamp());
            System.out.println(metadata.offset());
        }

    }
}
