package com.example.catalogservice.messagequeue;

import com.example.catalogservice.dto.OrderChangeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    public void send(String topic, OrderChangeDto orderChangeDto){

    }
}
