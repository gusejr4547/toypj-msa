package com.example.orderservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderProduceDto {
    private Long id;
    private String productId;
    private Integer qty;
}
