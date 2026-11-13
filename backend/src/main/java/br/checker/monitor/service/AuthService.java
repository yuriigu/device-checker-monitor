package br.checker.monitor.service;

import br.checker.monitor.config.JwtService;
import br.checker.monitor.dto.LoginRequest;
import br.checker.monitor.dto.LoginResponse;
import br.checker.monitor.model.User;
import br.checker.monitor.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                        identifier
                )
                .filter(candidate -> passwordEncoder.matches(request.password(), candidate.getPassword()))
                .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas"));

        String token = jwtService.generateToken(user.getEmail(), user.getRole().getName());
        return new LoginResponse(
                token,
                user.getName(),
                user.getEmail(),
                user.getRole().getName(),
                jwtService.getExpirationSeconds()
        );
    }
}
