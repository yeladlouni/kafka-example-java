import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerAvro {
    public static void main(String[] args) {
        Properties settings = new Properties();
        settings.put("client.id", "basic-producter-v0.0.1");
        settings.put("bootstrap.servers", "localhost:9092");
        settings.put(ConsumerConfig.GROUP_ID_CONFIG, "BasicConsumer");
        settings.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        settings.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        settings.put("schema.registry.url", "http://localhost:8081");
        settings.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        final String topic = "customer_avro_generic_topic";

        KafkaConsumer<String, GenericRecord> consumer = new KafkaConsumer<String, GenericRecord>(settings);
        consumer.subscribe(Collections.singletonList(topic));

        Duration timeout = Duration.ofMillis(100);

        while (true) {
            ConsumerRecords<String, GenericRecord> records = consumer.poll(timeout);

            for (ConsumerRecord<String, GenericRecord> record : records) {
                GenericRecord customer = record.value();
                System.out.println(customer);
            }

            consumer.commitSync();
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
