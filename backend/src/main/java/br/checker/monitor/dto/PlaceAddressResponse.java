package br.checker.monitor.dto;

/**
 * DTO de resposta para o endereço físico de um lugar.
 */
public record PlaceAddressResponse(

    String street,
    String number,
    String city,
    String state,
    String zipCode
) {
}