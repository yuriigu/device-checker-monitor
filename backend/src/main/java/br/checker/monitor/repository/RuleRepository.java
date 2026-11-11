package br.checker.monitor.repository;

import br.checker.monitor.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
    Optional<Rule> findByNameIgnoreCase(String name); //query que será utilizada em método para validar se ja possui uma regra com o mesmo nome
}