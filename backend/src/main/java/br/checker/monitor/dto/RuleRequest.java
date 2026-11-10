package br.checker.monitor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//DTO para cadastro de usuario(User)

public record RuleRequest(

        @NotBlank(message = "O nome da regra é obrigatório") //nome nao pode ser nulo
        String name,

        @NotBlank(message = "A ocorrencia da regra é obrigatória") //ocorrencia nao pode ser nula
        String occurence,

        @NotNull(message = "A severidade da regra é obrigatória") //severidade nao pode ser nula
        RuleSeverity severity,

        boolean applyToAll,
        Long deviceId,
        Long placeId,
) {
}