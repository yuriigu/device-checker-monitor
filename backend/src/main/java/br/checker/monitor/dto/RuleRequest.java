package br.checker.monitor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import br.checker.monitor.enums.RuleSeverity;

//DTO para resposta de cadastro de regra (Rule)

public record RuleRequest(

        @NotBlank(message = "O nome da regra é obrigatório") //nome nao pode ser nulo
        String name,

        @NotBlank(message = "A ocorrencia da regra é obrigatória") //ocorrencia nao pode ser nula
        String occurrence,

        @NotNull(message = "A severidade da regra é obrigatória") //severidade nao pode ser nula
        RuleSeverity severity,

        boolean applyToAll
) {
}