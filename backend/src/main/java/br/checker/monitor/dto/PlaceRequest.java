package br.checker.monitor.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO de entrada para cadastro e atualização de lugares (places).
 */
public record PlaceRequest(

    @NotBlank(message = "O nome do lugar é obrigatório")
    String name,

    Long parentId
) {
}