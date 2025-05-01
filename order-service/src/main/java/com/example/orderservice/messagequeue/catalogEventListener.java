package com.example.orderservice.messagequeue;

import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class catalogEventListener {
    private final OrderService orderService;

    @KafkaListener(topics = "change-order")
    public void changeOrderStatus(String decreaseProductMessage){
//        Map<Object, Object>
    }
}
