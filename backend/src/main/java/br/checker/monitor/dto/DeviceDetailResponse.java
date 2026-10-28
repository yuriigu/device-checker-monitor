package br.checker.monitor.dto;

import java.time.LocalTime;
import java.util.List;

/**
 * DTO de resposta completo para a tela detalhada de um dispositivo (HU001).
 *
 * <p>Contém todas as informações do dispositivo, incluindo dados da marca, modelo,
 * local e a lista completa de roles vinculadas.
 */
public record DeviceDetailResponse(

    Long id,
    String name,
    Long idBrand,
    String brandName,
    Long idModel,
    String modelName,
    String ipAddress,
    String macAddress,
    Long idPlace,
    String placeName,
    List<DeviceRoleResponse> deviceRoles
) {
}