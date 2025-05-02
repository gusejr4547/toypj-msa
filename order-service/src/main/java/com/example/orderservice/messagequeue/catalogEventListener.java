package com.example.orderservice.messagequeue;

import com.example.orderservice.dto.OrderChangeDto;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class catalogEventListener {
    private final OrderService orderService;

    @KafkaListener(topics = "stock-decreased")
    public void orderConfirm(OrderChangeDto confirm){
        orderService.orderConfirm(confirm.getId());
    }

    @KafkaListener(topics = "stock-unavailable")
    public void orderReject(OrderChangeDto reject){
        orderService.orderReject(reject.getId());
    }
}
