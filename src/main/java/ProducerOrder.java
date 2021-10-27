import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class ProducerOrder {
    public static void main(String[] args) {
        Properties settings = new Properties();
        settings.put("client.id", "customer-producer-v0.0.1");
        settings.put("bootstrap.servers", "localhost:9092");
        settings.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        settings.put("value.serializer", "OrderSerializer");

        final KafkaProducer<String, Order> producer = new KafkaProducer<String, Order>(settings);

        final String topic = "order_topic";

        for (int i = 1; i <= 1000000; i++) {
            final String key = "key-" + i;
            final Order order = new Order(i, i % 1000, i % 20, "Billing Address " + i,
                    "Shipping Address " + i);
            final ProducerRecord<String, Order> record = new ProducerRecord<>(topic, key, order);
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
