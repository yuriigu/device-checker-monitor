package br.checker.monitor.dto;

//DTO para responsta do cadastro de Regra (Rule)

public record RuleResponse(

        Long id,
        string Name,
        String occurence,
        RuleSeverity severity
        boolean applyToAll,
        Long deviceId,
        Long placeId
) {
}