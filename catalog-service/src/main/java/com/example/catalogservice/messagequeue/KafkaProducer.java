package com.example.catalogservice.messagequeue;

import com.example.catalogservice.dto.OrderChangeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, Object> orderChangeDtoKafkaTemplate;
    public void send(String topic, OrderChangeDto orderChangeDto){
        orderChangeDtoKafkaTemplate.send(topic, orderChangeDto);
        log.info("Kafka Producer sent data from the Catalog microservice: " + orderChangeDto);
    }
}
