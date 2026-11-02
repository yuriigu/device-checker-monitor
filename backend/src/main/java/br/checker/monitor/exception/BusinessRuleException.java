package br.checker.monitor.exception;

/**
 * Exceção lançada quando uma regra de negócio é violada.
 *
 * <p>Exemplos de uso:
 * <ul>
 *   <li>Tentar cadastrar endereço em um lugar que não é raiz (parent_id != null).</li>
 *   <li>Tentar cadastrar dispositivo com IP duplicado.</li>
 *   <li>Informar um modelo que não pertence à marca informada.</li>
 *   <li>Tentar cadastrar dispositivo sem pelo menos uma role.</li>
 * </ul>
 *
 * <p>O {@link br.checker.monitor.exception.GlobalExceptionHandler} captura esta exceção
 * e retorna HTTP {@code 400 Bad Request} (ou 409 Conflict, dependendo do caso).
 *
 * <p>Estende {@link RuntimeException} para ser não verificada.
 */
public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String message) {
        super(message);
    }
}