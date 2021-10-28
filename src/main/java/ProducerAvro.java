import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class ProducerAvro {
    public static void main(String[] args) {
        Properties settings = new Properties();
        settings.put("client.id", "basic-producter-v0.0.1");
        settings.put("bootstrap.servers", "localhost:9092");
        settings.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        settings.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        settings.put("schema.registry.url", "http://localhost:8081");

        final String topic = "customer_avro_topic";

        Producer<String, Customer> producer = new KafkaProducer<String, Customer>(settings);

        for (int i=1; i <= 1000; i++) {
            try {
                Customer customer = new Customer(i, "name-" + i);
                ProducerRecord<String, Customer> data = new ProducerRecord<>(topic, "key-" + i, customer);
                producer.send(data);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static class ProducerCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
            System.out.println(recordMetadata.topic());
            System.out.println(recordMetadata.partition());
            System.out.println(recordMetadata.offset());
            System.out.println(recordMetadata.timestamp());
        }
    }
}
