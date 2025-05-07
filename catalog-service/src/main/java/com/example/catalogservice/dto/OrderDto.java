package com.example.catalogservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderDto {
    private Long id;
    private String productId;
    private Integer qty;
}
