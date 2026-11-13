package br.checker.monitor.dto;

public record LoginResponse(
        String token,
        String name,
        String email,
        String role,
        long expiresIn
) {
}
