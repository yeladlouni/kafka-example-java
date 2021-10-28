import io.confluent.common.utils.Utils;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;

public class CustomPartitioner implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        if ((keyBytes == null) || (!(key instanceof String)))
                throw new InvalidRecordException("All customer messages should have a key");
        if (((String) key).equals("key-1"))
            return numPartitions - 1;

        return (Math.abs(Utils.murmur2(keyBytes)));
    }

    @Override
    public void close() {

    }


    @Override
    public void configure(Map<String, ?> map) {

    }
}
