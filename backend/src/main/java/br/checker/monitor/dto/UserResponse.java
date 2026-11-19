package br.checker.monitor.dto;

import br.checker.monitor.enums.RoleType;

// DTO de resposta para um usuario(User)

public record UserResponse(

        Long id,
        String name,
        String email,
        RoleType role

) {
}