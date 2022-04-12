package orderservice.messageque;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orderservice.dto.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {
    //메세지 전달
    private final KafkaTemplate<String, String> kafkaTemplate;
    //배열만들기 Arrays.asList
    //필드
    List<Field> fileds= Arrays.asList(
             new Field("string",true,"order_id"),
            new Field("string",true,"user_id"),
            new Field("string",true,"product_id"),
            new Field("int32",true,"qty"),
            new Field("int32",true,"unit_id"),
            new Field("int32",true,"total_price"));

     //빌더패턴이용 스키마
    Schema schema=Schema.builder()
             .type("struct")
             .fields(fileds)
             .optional(false)
             .name("orders")
             .build();


    //topic에서 바뀐 메세지 전달
    public OrderDto send(String topic, OrderDto orderDto) {
        //빌더패턴 이용 실질적인 데이터넣기
        Payload payload=Payload.builder()
                .order_id(orderDto.getOrderId())
                .user_id(orderDto.getUserId())
                .product_id(orderDto.getProductId())
                .qty(orderDto.getQty())
                .unit_price(orderDto.getUnitPrice())
                .total_price(orderDto.getTotalPrice())
                .build();

        //젤상단 앞에 구체적인내용을 위에 정해서 넣었음
        KafkaOrderDto kafkaOrderDto=new KafkaOrderDto(schema,payload);

        //OrderDto인스턴스 값을 json포멧으로 변경
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(kafkaOrderDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //보내기
        kafkaTemplate.send(topic, jsonInString);
        log.info("Kafka Producer sent data from the Order microservice:" + kafkaOrderDto);

        return orderDto;
    }
}
