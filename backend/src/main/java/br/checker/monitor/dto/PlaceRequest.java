package br.checker.monitor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO de entrada para cadastro e atualização de lugares (places).
 */
public record PlaceRequest(

    @NotBlank(message = "O nome do lugar é obrigatório")
    String name,

    Long parentId,

    @NotBlank(message = "A faixa de rede (CIDR) é obrigatória")
    @Pattern(regexp = "^((25[0-5]|2[0-4]\\d|1?\\d?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1?\\d?\\d)/(3[0-2]|[12]?\\d)$",
            message = "CIDR inválido (ex.: 192.168.0.0/24)")
    String cidr
) {
}