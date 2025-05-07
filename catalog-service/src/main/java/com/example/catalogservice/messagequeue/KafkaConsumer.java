package com.example.catalogservice.messagequeue;

import com.example.catalogservice.dto.OrderChangeDto;
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
    private final KafkaProducer kafkaProducer;


    @KafkaListener(topics = "example-catalog-topic", containerFactory = "stringListenerContainerFactory")
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

    @KafkaListener(topics = "decrease-stock", containerFactory = "OrderDtoListenerContainerFactory")
    public void decreaseStockListen(OrderDto orderDto) {
        log.info("Kafka Message: -> " + orderDto);

        OrderChangeDto orderChangeDto;
        try {
            catalogService.decrementStock(orderDto);
            orderChangeDto = new OrderChangeDto(orderDto.getId(), "Confirm", "Decrement Success");
            kafkaProducer.send("stock-decreased", orderChangeDto);

        } catch (Exception e) {
            orderChangeDto = new OrderChangeDto(orderDto.getId(), "Fail", e.getMessage());
            kafkaProducer.send("stock-unavailable", orderChangeDto);
        }
    }
}
