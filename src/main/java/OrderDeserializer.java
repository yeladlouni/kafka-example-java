import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class OrderDeserializer implements Deserializer<Order> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Order deserialize(String s, byte[] data) {
        try {
            return objectMapper.readValue(new String(data, "UTF-8"), Order.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
