package br.checker.monitor.repository;

import br.checker.monitor.model.PlaceRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRuleRepository extends JpaRepository<PlaceRule, Long> {
}