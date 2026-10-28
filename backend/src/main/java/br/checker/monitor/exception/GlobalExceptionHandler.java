package br.checker.monitor.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * Tratador global de exceções para todos os controllers REST da aplicação.
 *
 * <p>Anotacao {@link RestControllerAdvice}: intercepta excecoes lancadas por qualquer
 * RestController e converte em respostas HTTP com corpo JSON estruturado.
 *
 * <p>Cada metodo anotado com {@link ExceptionHandler} captura um tipo específico
 * de excecao e retorna o status HTTP apropriado.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Estrutura padrao de resposta de erro.
     */
    public record ErrorResponse(String message, List<String> details) {
    }

    /**
     * Captura ResourceNotFoundException -> HTTP 404.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage(), null));
    }

    /**
     * Captura BusinessRuleException -> HTTP 400.
     */
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRule(BusinessRuleException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), null));
    }

    /**
     * Captura falhas de validacao do Bean Validation (@NotBlank, @NotNull, etc.)
     * quando um DTO anotado com @Valid falha.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> details = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            details.add("Campo '" + fieldError.getField() + "': " + fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Erro de validacao nos dados enviados", details));
    }

    /**
     * Captura ConstraintViolationException (validacao em parametros de metodo).
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> details = new ArrayList<>();
        ex.getConstraintViolations().forEach(v ->
                details.add(v.getPropertyPath() + ": " + v.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Erro de validacao de constraint", details));
    }

    /**
     * Captura DataIntegrityViolationException (unique constraint, FK invalida, etc.)
     * -> HTTP 409 Conflict.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "Violacao de integridade de dados: " + extractMessage(ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(message, null));
    }

    /**
     * Fallback para qualquer excecao nao tratada -> HTTP 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Erro interno do servidor: " + ex.getMessage(), null));
    }

    /**
     * Extrai a mensagem mais especifica da excecao, incluindo a causa raiz.
     */
    private String extractMessage(DataIntegrityViolationException ex) {
        String msg = ex.getMessage() != null ? ex.getMessage() : "";
        Throwable cause = ex.getCause();
        while (cause != null) {
            if (cause.getMessage() != null && !cause.getMessage().isBlank()) {
                msg = cause.getMessage();
            }
            cause = cause.getCause();
        }
        return msg;
    }
}