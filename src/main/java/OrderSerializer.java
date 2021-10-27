import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class OrderSerializer implements Serializer<Order> {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String s, Order order) {
        try {
            return objectMapper.writeValueAsBytes(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
