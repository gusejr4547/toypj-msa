package com.example.catalogservice.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderDto implements Serializable {
    private Long id;
    private String productId;
    private Integer qty;
}
