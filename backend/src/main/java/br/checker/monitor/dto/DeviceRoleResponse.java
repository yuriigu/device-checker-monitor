package br.checker.monitor.dto;

import java.time.LocalTime;

/**
 * DTO de resposta para um vínculo DeviceRole na tela detalhada do dispositivo.
 */
public record DeviceRoleResponse(

    Long idRole,
    String roleName,
    LocalTime timeStart,
    LocalTime timeEnd,
    Boolean active
) {
}