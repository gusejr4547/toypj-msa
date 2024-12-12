package com.example.orderservice.mapper;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderEntity toOrderEntity(OrderDto orderDto);
    OrderDto toOrderDto(OrderEntity orderEntity);

    OrderDto requestOrderToOrderDto(RequestOrder order);

    ResponseOrder orderDtoToResponseOrder(OrderDto orderDto);
    ResponseOrder toResponseOrder(OrderEntity orderEntity);
}
