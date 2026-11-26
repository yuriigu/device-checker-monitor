package br.checker.monitor.service;

import br.checker.monitor.config.JwtService;
import br.checker.monitor.dto.LoginRequest;
import br.checker.monitor.dto.LoginResponse;
import br.checker.monitor.dto.RefreshTokenRequest;
import br.checker.monitor.dto.RefreshTokenResponse;
import br.checker.monitor.model.User;
import br.checker.monitor.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        String identifier = request.login().trim();
        User user = userRepository.findByEmailIgnoreCaseOrNameIgnoreCaseOrPhone(
                        identifier,
                        identifier,
                        identifier)
                .filter(candidate -> passwordEncoder.matches(request.password(), candidate.getPassword()))
                .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas"));

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return new LoginResponse(
                token,
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                jwtService.getExpirationSeconds()
        );
    }

    @Transactional(readOnly = true)
    public RefreshTokenResponse refreshToken(@Valid RefreshTokenRequest request) {
        String token = request.getToken();

        if (token == null || token.isBlank()) {
            throw new BadCredentialsException("Token is required");
        }

        try {
            Claims claims = jwtService.parseToken(token);
            String subject = claims.getSubject();

            if (subject == null) {
                throw new BadCredentialsException("Invalid token: subject is null");
            }

            // verifica se o token já está expirado
            Date expiration = claims.getExpiration();
            if (expiration == null || expiration.before(new java.util.Date())) {
                throw new BadCredentialsException("Token has expired");
            }

            String newToken = jwtService.generateToken(subject, claims.get("role", String.class));

            return new RefreshTokenResponse(newToken, "Token refreshed successfully");
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid token: " + e.getMessage());
        }
    }
}
