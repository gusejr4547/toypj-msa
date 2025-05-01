package com.example.catalogservice.messagequeue;

import com.example.catalogservice.dto.OrderDto;
import com.example.catalogservice.entity.CatalogEntity;
import com.example.catalogservice.repository.CatalogRepository;
import com.example.catalogservice.service.CatalogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final CatalogRepository catalogRepository;
    private final CatalogService catalogService;


    @KafkaListener(topics = "example-catalog-topic")
    public void updateQty(String kafkaMessage) {
        log.info("Kafka Message: -> " + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {
            });
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        CatalogEntity catalogEntity = catalogRepository.findByProductId((String) map.get("productId"));
        if (catalogEntity != null) {
            catalogEntity.setStock(catalogEntity.getStock() - (Integer) map.get("qty"));
            catalogRepository.save(catalogEntity);
        }
    }

    @KafkaListener(topics = "decrease-stock")
    public void decreaseStockListen(OrderDto orderDto) {
        log.info("Kafka Message: -> " + orderDto);

        try {
            catalogService.decrementStock(orderDto);
            System.out.println("성공");
        }catch (Exception e){
            System.out.println("실패");
        }

        System.out.println("kafka 보내기");
    }
}
