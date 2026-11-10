package br.checker.monitor.repository;

import br.checker.monitor.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório de dados para a entidade {@link Place} (tabela places).
 */
@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
    boolean existsByParentId(Long parentId);
}