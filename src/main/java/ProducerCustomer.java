import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class ProducerCustomer {
    public static void main(String[] args) {
        Properties settings = new Properties();
        settings.put("client.id", "customer-producer-v0.0.1");
        settings.put("bootstrap.servers", "localhost:9092");
        settings.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        settings.put("value.serializer", "CustomerSerializer");
        settings.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class);

        final KafkaProducer<String, Customer> producer = new KafkaProducer<String, Customer>(settings);

        final String topic = "customer_topic";

        for (int i = 1; i <= 1000000; i++) {
            final String key = "key-" + i;
            final Customer customer = new Customer(i, "customer-" + i);
            final ProducerRecord<String, Customer> record = new ProducerRecord<>(topic, key, customer);
            producer.send(record, new ProducerCallback());
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
