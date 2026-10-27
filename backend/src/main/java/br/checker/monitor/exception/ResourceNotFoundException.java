package br.checker.monitor.exception;

/**
 * Exceção lançada quando um recurso solicitado não é encontrado no banco de dados.
 *
 * <p>O {@link br.checker.monitor.exception.GlobalExceptionHandler} captura esta exceção
 * e retorna uma resposta HTTP {@code 404 Not Found} com o corpo da mensagem de erro.
 *
 * <p>Estende {@link RuntimeException} para ser uma exceção não verificada (unchecked),
 * ou seja, não precisa ser declarada nos {@code throws} dos métodos.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}