package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final KafkaProducer kafkaProducer;

    @Transactional
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

//        OrderEntity orderEntity = orderMapper.toOrderEntity(orderDto);

        OrderEntity orderEntity = OrderEntity.builder()
                .orderId(orderDto.getOrderId())
                .productId(orderDto.getProductId())
                .qty(orderDto.getQty())
                .totalPrice(orderDto.getTotalPrice())
                .unitPrice(orderDto.getUnitPrice())
                .userId(orderDto.getUserId())
                .orderStatus("PENDING")
                .build();

        orderRepository.save(orderEntity);

        /* send this order to the kafka */
        kafkaProducer.send("decrease-product", orderDto);

        return orderMapper.toOrderDto(orderEntity);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        return orderMapper.toOrderDto(orderEntity);
    }

    @Override
    public List<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
