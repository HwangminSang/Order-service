package orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

//반환용 클라리언트에게
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //null값은 반환안함
public class ResponseOrder {

    private  String productId;
    private  Integer qty;
    private  Integer unitPrice;
    private  Integer totalPrice;
    private Date createdAt;
    private  String orderId;


}
