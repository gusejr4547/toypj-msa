package com.example.catalogservice.mapper;

import com.example.catalogservice.entity.CatalogEntity;
import com.example.catalogservice.vo.ResponseCatalog;
import org.mapstruct.Mapper;

@Mapper
public interface CatalogMapper {
    ResponseCatalog toResponseCatalog(CatalogEntity catalogEntity);
}
