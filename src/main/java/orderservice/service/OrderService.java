package orderservice.service;


import orderservice.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId, String userId);
    List<OrderDto> getOrdersByUserId(String userId);


}
