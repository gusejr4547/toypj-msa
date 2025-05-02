package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.OrderProduceDto;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.messagequeue.OrderProducer;
import com.example.orderservice.repository.OrderRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        OrderProduceDto orderProduceDto = new OrderProduceDto(orderEntity.getId(), orderEntity.getProductId(), orderEntity.getQty());
        /* send this order to the kafka */
        kafkaProducer.send("decrease-stock", orderProduceDto);

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

    @Transactional
    @Override
    public void orderConfirm(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Order"));
        order.changeOrderStatus("Confirmed");
    }

    @Transactional
    @Override
    public void orderReject(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Order"));
        order.changeOrderStatus("Rejected");
    }
}
