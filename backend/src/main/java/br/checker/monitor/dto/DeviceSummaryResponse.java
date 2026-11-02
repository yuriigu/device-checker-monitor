package br.checker.monitor.dto;

/**
 * DTO de resposta simplificado para listagem de dispositivos em tabela.
 *
 * <p>Contém apenas os campos essenciais para exibição em uma listagem (grid/tabela)
 * na tela do frontend, evitando expor desnecessariamente todos os detalhes.
 *
 * <p>Java {@code record}: tipo imutável ideal para transferência de dados de resposta.
 */
public record DeviceSummaryResponse(

    Long id,
    String name,
    String ipAddress,
    String macAddress,
    String brandName,
    String modelName,
    String placeName
) {
}