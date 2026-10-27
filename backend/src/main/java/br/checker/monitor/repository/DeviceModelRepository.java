package br.checker.monitor.repository;

import br.checker.monitor.model.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório de dados para a entidade {@link DeviceModel} (tabela models).
 */
@Repository
public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long> {
}