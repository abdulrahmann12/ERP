package com.learn.erp.events;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordCodeRegeneratedEvent {
    private String email;
    private String username;
    private String code;
}