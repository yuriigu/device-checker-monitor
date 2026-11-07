package br.checker.monitor.dto;

import jakarta.validation.constraints.NotBlank;

public record PlaceAddressRequest(
        @NotBlank(message = "A rua é obrigatória") String street,
        String number,
        @NotBlank(message = "A cidade é obrigatória") String city,
        @NotBlank(message = "O estado é obrigatório") String state,
        String zipCode) {}