package orderservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

//스키마안에 포함
@Data
@AllArgsConstructor   //필드
public class Field {
     //소스커넥터에서 싱크커넥트에게 보낼때 이러한 필드가 가진 값들이 필요함
    private  String type;
    private  boolean optional;
    private  String field;
}
