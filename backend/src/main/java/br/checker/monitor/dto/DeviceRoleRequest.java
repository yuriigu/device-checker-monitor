package br.checker.monitor.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

/**
 * DTO de entrada (request) para um vínculo de dispositivo com uma role.
 *
 * <p>Usado no cadastro e atualização de dispositivos para informar quais roles
 * o dispositivo terá, com horário de início/fim e status ativo/inativo.
 *
 * <p>Java {@code record}: tipo imutável e conciso ideal para DTOs.
 * O compilador gera automaticamente construtor, getters, equals, hashCode e toString.
 *
 * <p>Anotações de validação ({@link jakarta.validation}):
 * <ul>
 *   <li>{@link NotNull}: o campo não pode ser nulo.</li>
 * </ul>
 */
public record DeviceRoleRequest(

    @NotNull(message = "O ID da role é obrigatório")
    Long idRole,

    @NotNull(message = "O horário de início é obrigatório")
    LocalTime timeStart,

    @NotNull(message = "O horário de término é obrigatório")
    LocalTime timeEnd,

    Boolean active
) {
}