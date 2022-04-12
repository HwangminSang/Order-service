package orderservice.dto;


import lombok.Builder;
import lombok.Data;

//각각
//마리아 디비에 칼럼값과 일치해야한다
@Data
@Builder //빌더로 데이터 저장
public class Payload {
       private String order_id;
       private String user_id;
       private String product_id;
       private  int qty;
       private  int unit_price;
       private  int total_price;
}
