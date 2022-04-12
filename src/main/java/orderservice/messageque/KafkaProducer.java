package orderservice.messageque;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orderservice.dto.OrderDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    //메세지 전달
    private final KafkaTemplate<String, String> kafkaTemplate;

    //topic에서 바뀐 메세지 전달
    public OrderDto send(String topic, OrderDto orderDto) {
        //OrderDto인스턴스 값을 json포멧으로 변경
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(orderDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //보내기
        kafkaTemplate.send(topic, jsonInString);
        log.info("Kafka Producer sent data from the Order microservice:" + orderDto);
        return orderDto;
    }
}
