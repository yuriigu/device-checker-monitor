package br.checker.monitor.repository;

import br.checker.monitor.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório de dados para a entidade {@link Role} (tabela roles).
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}