package br.checker.monitor.dto;

//DTO para cadastro de usuario(User)

public record RuleRequest(

        Long id,
        string Name,
        String occurence,
        RuleSeverity severity
        boolean applyToAll,
        Long deviceId,
        Long placeId,
        List<Long> notifiedUserIds //lista de ids de quem será notificado
) {
}