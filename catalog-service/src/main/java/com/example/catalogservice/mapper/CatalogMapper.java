package com.example.catalogservice.mapper;

import com.example.catalogservice.entity.CatalogEntity;
import com.example.catalogservice.vo.ResponseCatalog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatalogMapper {
    ResponseCatalog toResponseCatalog(CatalogEntity catalogEntity);
}
