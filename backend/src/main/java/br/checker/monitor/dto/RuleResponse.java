package br.checker.monitor.dto;

//DTO para responsta do cadastro de Regra (Rule)
import br.checker.monitor.enums.RuleSeverity;

public record RuleResponse(

        Long id,
        String Name,
        String occurence,
        RuleSeverity severity,
        boolean applyToAll
) {
}