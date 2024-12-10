package com.example.userservice.vo;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUser {
    private String email;
    private String name;
    private String userId;
}
