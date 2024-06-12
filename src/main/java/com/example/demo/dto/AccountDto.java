package com.example.demo.dto;

public record AccountDto(
        String id,
        String iban,
        String firstName,
        String lastName
) {
}
