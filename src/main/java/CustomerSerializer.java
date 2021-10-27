import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CustomerSerializer implements Serializer<Customer> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        //TODO
    }

    @Override
    public void close() {
        //TODO
    }

    @Override
    public byte[] serialize(String s, Customer customer) {
        try {
            byte[] serializedName;
            int stringSize;
            if (customer == null)
                return null;
            else {
                if (customer.getName() != null) {
                    serializedName = customer.getName().getBytes(StandardCharsets.UTF_8);
                    stringSize = serializedName.length;
                } else {
                    serializedName = new byte[0];
                    stringSize = 0;
                }

                ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + stringSize);
                buffer.putInt(customer.getId());
                buffer.putInt(stringSize);
                buffer.put(serializedName);

                return buffer.array();
            }
        } catch (Exception e) {
            throw new SerializationException("Error during serialization of customer" + e);
        }
    }
}
