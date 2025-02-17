package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.messagequeue.OrderProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {
    private final Environment env;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s", env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrder order) {
        OrderDto orderDto = orderMapper.requestOrderToOrderDto(order);
        orderDto.setUserId(userId);
        /* jpa */
        OrderDto createdOrder = orderService.createOrder(orderDto);
        ResponseOrder responseOrder = orderMapper.orderDtoToResponseOrder(createdOrder);

        /* kafka */
//        orderDto.setOrderId(UUID.randomUUID().toString());
//        orderDto.setTotalPrice(order.getQty() * order.getUnitPrice());

        /* send this order to the kafka */
//        kafkaProducer.send("example-catalog-topic", orderDto);
//        orderProducer.send("orders", orderDto);

//        ResponseOrder responseOrder = orderMapper.orderDtoToResponseOrder(orderDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) {
        List<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> result.add(orderMapper.toResponseOrder(v)));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
