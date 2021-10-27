import kafka.Kafka;
import kafka.server.KafkaConfig;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class ProducerAsync {
    public static void main(String[] args) {
        Properties settings = new Properties();
        settings.put("client.id", "basic-producter-v0.0.1");
        settings.put("bootstrap.servers", "localhost:9092");
        settings.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        settings.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // client.id
        // acks: 0,1,all

        /* delivery duration */
        // max.block.ms
        // delivery.timeout.ms
        // request.timeout.ms
        // retries
        // retry.backoff.ms (100ms)
        // linger.ms
        // buffer.memory

        // compression.type: snappy, lz4, zstd
        // batch.size
        // max.in.flight.requests.per.connection  --> throughput (nombre d'octets écrits par seconde)
        // max.request.size
        // receive.buffer.bytes
        // send.buffer.bytes

        // enable.idompotence

        final KafkaProducer<String, String> producer = new KafkaProducer<String, String>(settings);

        final String topic = "hello_world_async_topic";

        for (int i = 1; i <= 1000000; i++) {
            final String key = "key-" + i;
            final String value = "value-" + i;
            final ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
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
