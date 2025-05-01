package com.example.catalogservice.service;

import com.example.catalogservice.dto.OrderDto;
import com.example.catalogservice.entity.CatalogEntity;
import com.example.catalogservice.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.InsufficientResourcesException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {
    private final CatalogRepository catalogRepository;

    @Override
    public List<CatalogEntity> getAllCatalogs() {
        return catalogRepository.findAll();
    }

    @Transactional
    @Override
    public void decrementStock(OrderDto orderDto) {
        CatalogEntity catalogEntity = catalogRepository.findByProductId(orderDto.getProductId());
        int currentStock = catalogEntity.getStock();
        int orderStock = orderDto.getQty();

        if (currentStock < orderStock) {
            throw new InsufficientResourcesException("재고가 부족합니다. id: " + catalogEntity.getProductId());
        }

        catalogEntity.decrementStock(orderStock);
    }
}
