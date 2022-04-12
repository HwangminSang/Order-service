package orderservice.entity;


import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="orders")
//직렬화 이유는? 마샬링 작업을 하기 위해서
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//고유값
    private  Long id;


    @Column(nullable = false,length = 120,unique = true)
    private  String productId;
    @Column(nullable = false)
    private  Integer qty;
    @Column(nullable = false)
    private Integer unitPrice;
    @Column(nullable = false)
    private  Integer totalPrice;

    @Column(nullable = false)
    private  String userId;

    @Column(nullable = false,unique = true)
    private  String orderId;

    @Column(nullable = false,updatable = false,insertable = false)
    @ColumnDefault(value="CURRENT_TIMESTAMP") //함수호출
    private Date createdAt;


}
