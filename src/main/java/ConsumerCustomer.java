import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONObject;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;

public class ConsumerCustomer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "BasicConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomerDeserializer.class);

        KafkaConsumer<String, Customer> consumer = new KafkaConsumer(props);

        consumer.subscribe(Collections.singletonList("customer_topic"));

        // fetch.min.bytes
        // fetch.max.wait.ms
        // fetch.max.bytes
        // max.poll.records
        // max.partition.fetch.bytes
        // session.timeout.ms
        // heartbeat.interval.ms
        // max.poll.interval.ms
        // default.api.timeout.ms
        // request.timeout.ms
        // auto.offset.reset
        // enable.auto.commit
        // partition.assignment.strategy Range RoundRobin Sticky Cooperative Sticky
        // client.id
        // client.rack
        // group.instance.id
        // offsets.retention.minutes

        //consumer.subscribe(Pattern.compile("hello_world_topic*"));

        // Pull/Poll Loop

        Duration timeout = Duration.ofMillis(100);

        HashMap<String, String> map = new HashMap<>();

        while (true) {
            ConsumerRecords<String, Customer> records = consumer.poll(timeout);

            for (ConsumerRecord<String, Customer> record : records) {
                System.out.printf("topic = %s, partition = %d, offset = %d, key = %s, value = %s",
                        record.topic(),
                        record.partition(),
                        record.offset(),
                        record.key(),
                        record.value());

                JSONObject json = new JSONObject(record);
                System.out.println(json.toString());
            }

        }




    }
}
