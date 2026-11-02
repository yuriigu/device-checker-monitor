package br.checker.monitor.dto;

/**
 * DTO de resposta para um lugar (place), podendo incluir seu endereço.
 */
public record PlaceResponse(

    Long id,
    String name,
    Long parentId,
    PlaceAddressResponse address
) {
}