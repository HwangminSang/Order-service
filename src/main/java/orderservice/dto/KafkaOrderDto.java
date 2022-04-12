package orderservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


//제일 상단
@Data
@AllArgsConstructor
public class KafkaOrderDto implements Serializable {//직렬화
    private Schema schema;
    private  Payload payload;

}
