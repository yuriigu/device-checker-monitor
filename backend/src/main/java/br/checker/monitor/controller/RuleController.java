package br.checker.monitor.controller;

import br.checker.monitor.dto.RuleResponse;
import br.checker.monitor.dto.RuleRequest;
import br.checker.monitor.service.RuleService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping //rota de post (criar regra)
    public ResponseEntity<RuleResponse> createRule(@Valid @RequestBody RuleRequest request) {
        RuleResponse created = ruleService.createRule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping // rota de get (listar todas as regras)
    public ResponseEntity<List<RuleResponse>> findAll() {
        List<RuleResponse> rules = ruleService.findAll();
        return ResponseEntity.ok(rules);
    }


    @GetMapping("/{id}") //rota de get por id (obter unica regra)
    public ResponseEntity<RuleResponse>

    findById(@PathVariable Long id) {
        RuleResponse rule = ruleService.findById(id);
        return ResponseEntity.ok(rule);
    }

    @PostMapping("/{ruleId}/devices/{deviceId}") // vincula uma regra a um dispositivo
    public ResponseEntity<Void> linkRuleToDevice(
            @PathVariable Long ruleId,
            @PathVariable Long deviceId) {
        ruleService.linkRuleToDevice(ruleId, deviceId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{ruleId}/places/{placeId}") // vincula uma regra a um local
    public ResponseEntity<Void> linkRuleToPlace(
            @PathVariable Long ruleId,
            @PathVariable Long placeId) {
        ruleService.linkRuleToPlace(ruleId, placeId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}