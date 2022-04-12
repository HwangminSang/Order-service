package orderservice.service;



import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orderservice.dto.OrderDto;
import orderservice.entity.OrderEntity;
import orderservice.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements  OrderService{
   private  final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {




        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderEntity=mapper.map(orderDto, OrderEntity.class);


        orderRepository.save(orderEntity);

        OrderDto returnValue=mapper.map(orderEntity, OrderDto.class);
        return returnValue;
    }



    @Override
    public OrderDto getOrderByOrderId(String userId, String orderId) {

        //해당 사용자의 전체 주문 내역 찾아와서
//        List<OrderEntity> orderEntityList=orderRepository.findByUserId(userId);
//        //해당 주문이 있는지 확인
//        System.out.println(orderEntityList);
//        OrderEntity fiterOrederEntity=orderEntityList.stream().filter(v->
//           v.getOrderId().equals(orderId)).findFirst().orElseThrow(()->new IllegalArgumentException());
//
//        OrderDto returnValue=new ModelMapper().map(fiterOrederEntity,  OrderDto.class);


        OrderEntity order=orderRepository.findByOrderId(orderId);

        if(!order.getUserId().equals(userId)){
            throw new IllegalArgumentException();
        }
        OrderDto returnValue1=new ModelMapper().map(order,  OrderDto.class);

        return returnValue1;
    }



    @Override
    public List<OrderDto> getOrdersByUserId(String userId) {

        List<OrderDto> orderDtoList=new ArrayList<>();
        List<OrderEntity> orderEntityList=orderRepository.findByUserId(userId);

        orderEntityList.forEach(OrderEntity->{
            orderDtoList.add(new ModelMapper().map(OrderEntity,OrderDto.class));
        });

        return orderDtoList;
    }
}
