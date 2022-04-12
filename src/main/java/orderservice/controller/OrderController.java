package orderservice.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orderservice.dto.OrderDto;

import orderservice.messageque.KafkaProducer;
import orderservice.messageque.OrderProducer;
import orderservice.service.OrderService;
import orderservice.vo.RequestOrder;
import orderservice.vo.ResponseOrder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {

    private  final OrderService orderService;
    private  final KafkaProducer kafkaProducer;
    //카프카이용
    private  final OrderProducer orderProducer;

    //누가 주문한지 알기 위해서 앞에   userId
      @PostMapping("/{userId}/orders")
      public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId")String userId
                                                        , @RequestBody RequestOrder orderDetails){
          log.info("order-service Post 도착");

          OrderDto orderDto=new ModelMapper().map(orderDetails,OrderDto.class);
          orderDto.setUserId(userId);

          orderDto.setOrderId(UUID.randomUUID().toString());
          orderDto.setTotalPrice(orderDetails.getQty()*orderDetails.getUnitPrice());
          /* jpa */
//          //데이터 저장
        OrderDto returnValue=orderService.createOrder(orderDto);
          ResponseOrder responseOrder=new ModelMapper().map(orderDto,ResponseOrder.class);
          /* kafka */
//         orderDto.setOrderId(UUID.randomUUID().toString());
//         orderDto.setTotalPrice(orderDetails.getQty()*orderDetails.getUnitPrice());

          /**
           *   send this order to the kafka
           *    같은 서버 같은 디비끼리 동기화역할할           */

          kafkaProducer.send("example-catalog-topic",orderDto);
//          orderProducer.send("orders",orderDto);



          log.info("order-service Post 나가기");
          return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
      }

      //한명이 여러개를 주문가능
      @GetMapping("/{userId}/orders")
     public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable("userId")  String userId) {
            //주문내역
           log.info("order-service Get 도착");
            List<OrderDto>  orderDtoList=orderService.getOrdersByUserId(userId);
            //응답반환
            List<ResponseOrder> responseOrderList=new ArrayList<>();
            orderDtoList.forEach(orderDto->{
                responseOrderList.add(new ModelMapper().map(orderDto,  ResponseOrder.class));
            });
//            try{
//                Thread.sleep(1000);
//                throw  new Exception("장애발생");
//            }catch (InterruptedException e){
//                log.warn(e.getMessage());
//            }
          log.info("order-service Get 나가기");
            //응답 ok
            return ResponseEntity.status(HttpStatus.OK).body(responseOrderList);
    }

    //한개의 주문
    //여러개 일때는 {}필요
    @GetMapping(value = {"/{userId}/orders/{orderId}"})
    public ResponseEntity<ResponseOrder> getOrder(@PathVariable("userId")  String userId,@PathVariable("orderId")  String orderId) {
        //주문내역
        OrderDto orderDto=orderService.getOrderByOrderId(userId,orderId);
        //응답반환
        ModelMapper mapper=new ModelMapper();
        ResponseOrder responseOrder=mapper.map(orderDto,  ResponseOrder.class);



        //응답 ok
        return ResponseEntity.status(HttpStatus.OK).body(responseOrder);
    }
}
