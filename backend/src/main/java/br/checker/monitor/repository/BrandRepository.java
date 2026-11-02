package br.checker.monitor.repository;

import br.checker.monitor.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório de dados para a entidade {@link Brand}.
 *
 * <p>Estende {@link JpaRepository}, que fornece automaticamente métodos CRUD
 * (save, findById, findAll, deleteById, etc.) baseados em JPA.
 * Não é necessário escrever nenhuma implementação — o Spring Data JPA gera
 * a implementação em tempo de execução via proxies.
 *
 * <p>Anotação {@link Repository}: indica ao Spring que esta é uma camada de acesso a dados,
 * permitindo tradução de exceções de persistência para exceções do Spring.
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}