package com.example.catalogservice.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderChangeDto {
    private Long id;
    private String status;
    private String message;
}
