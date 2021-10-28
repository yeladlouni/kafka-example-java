import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.common.serialization.Deserializer;

public class CustomerDeserializer implements Deserializer<Customer> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Customer deserialize(String s, byte[] data) {
        try {
            return objectMapper.readValue(new String(data).replaceAll("[\\\\x00-\\\\x09\\\\x0B\\\\x0C\\\\x0E-\\\\x1F\\\\x7F]", ""), Customer.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
