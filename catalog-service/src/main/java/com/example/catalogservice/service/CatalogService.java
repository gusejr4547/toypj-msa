package com.example.catalogservice.service;


import com.example.catalogservice.dto.OrderDto;
import com.example.catalogservice.entity.CatalogEntity;

import java.util.List;

public interface CatalogService {
    List<CatalogEntity> getAllCatalogs();

    void decrementStock(OrderDto orderDto);
}
