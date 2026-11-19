package br.checker.monitor.repository;

import br.checker.monitor.model.DeviceRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRuleRepository extends JpaRepository<DeviceRule, Long> {
}