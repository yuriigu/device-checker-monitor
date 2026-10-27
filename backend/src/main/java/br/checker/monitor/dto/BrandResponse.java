package br.checker.monitor.dto;

/**
 * DTO de resposta simplificado para uma marca (brand).
 */
public record BrandResponse(

    Long id,
    String name
) {
}