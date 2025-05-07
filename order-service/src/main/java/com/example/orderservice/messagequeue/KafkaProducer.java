package com.example.orderservice.messagequeue;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.OrderProduceDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
//    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, Object> catalogKafkaTemplate;

//    public OrderDto send(String topic, OrderDto orderDto) {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = "";
//        try {
//            jsonInString = mapper.writeValueAsString(orderDto);
//        } catch (JsonProcessingException ex) {
//            ex.printStackTrace();
//        }
//
//        kafkaTemplate.send(topic, jsonInString);
//        log.info("Kafka Producer sent data from the Order microservice: " + orderDto);
//
//        return orderDto;
//    }

    public void send(String topic, OrderProduceDto orderProduceDto) {
        catalogKafkaTemplate.send(topic, orderProduceDto);
        log.info("Kafka Producer sent data from the Order microservice: " + orderProduceDto);
    }

}
