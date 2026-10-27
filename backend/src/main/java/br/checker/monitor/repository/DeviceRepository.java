package br.checker.monitor.repository;

import br.checker.monitor.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório de dados para a entidade {@link Device} (tabela devices).
 *
 * <p>A classe esquelética existente foi convertida em uma {@code interface} estendendo
 * {@link JpaRepository<Device, Long>}, assim o Spring Data JPA fornece automaticamente
 * todos os métodos CRUD padrão.
 *
 * <p>Adicionamos um método de consulta derivado (query method):
 * <ul>
 *   <li>{@code existsByIpAddress}: verifica se já existe um dispositivo com o IP informado.
 *       O Spring Data JPA interpreta o nome do método e gera a consulta SQL automaticamente.</li>
 * </ul>
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    /**
     * Verifica se já existe um dispositivo cadastrado com o endereço IP informado.
     * Útil para aplicar a regra de unicidade de IP antes de tentar salvar.
     *
     * @param ipAddress endereço IP a ser verificado
     * @return true se já existe um dispositivo com este IP, false caso contrário
     */
    boolean existsByIpAddress(String ipAddress);

    /**
     * Busca um dispositivo pelo endereço IP.
     * Útil na atualização para verificar se o IP já pertence a outro dispositivo.
     *
     * @param ipAddress endereço IP a ser buscado
     * @return o dispositivo com esse IP, ou null se não existir
     */
    Device findByIpAddress(String ipAddress);
}
