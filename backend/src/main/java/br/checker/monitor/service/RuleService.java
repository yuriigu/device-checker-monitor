package br.checker.monitor.service;

import br.checker.monitor.dto.RuleRequest;
import br.checker.monitor.dto.RuleResponse;
import br.checker.monitor.model.Device;
import br.checker.monitor.model.DeviceRule;
import br.checker.monitor.model.Place;
import br.checker.monitor.model.PlaceRule;
import br.checker.monitor.model.Rule;
import br.checker.monitor.repository.DeviceRepository;
import br.checker.monitor.repository.DeviceRuleRepository;
import br.checker.monitor.repository.PlaceRepository;
import br.checker.monitor.repository.PlaceRuleRepository;
import br.checker.monitor.repository.RuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RuleService {

    private final RuleRepository ruleRepository;
    private final DeviceRepository deviceRepository;
    private final PlaceRepository placeRepository;
    private final DeviceRuleRepository deviceRuleRepository;
    private final PlaceRuleRepository placeRuleRepository;

    // é como se fosse o get_db do FastAPI, no momento do springboot instanciar na memória as conexões necessarias com as tabelas necessarias, são passadas as que realmente são necessárias:
    public RuleService(
            RuleRepository ruleRepository,
            DeviceRepository deviceRepository,
            PlaceRepository placeRepository,
            DeviceRuleRepository deviceRuleRepository,
            PlaceRuleRepository placeRuleRepository) {
        this.ruleRepository = ruleRepository;
        this.deviceRepository = deviceRepository;
        this.placeRepository = placeRepository;
        this.deviceRuleRepository = deviceRuleRepository;
        this.placeRuleRepository = placeRuleRepository;
    }

    public List<RuleResponse> findAll() {
        return ruleRepository.findAll()
                .stream()
                .map(rule -> new RuleResponse(
                        rule.getId(),
                        rule.getName(),
                        rule.getOccurrence(),
                        rule.getSeverity(),
                        rule.isApplyToAll()
                ))
                .toList();
    }

    public RuleResponse findById(Long id) {
        Rule rule = ruleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Regra não encontrada."));

        return new RuleResponse(
                rule.getId(),
                rule.getName(),
                rule.getOccurrence(),
                rule.getSeverity(),
                rule.isApplyToAll()
        );
    }

    public void linkRuleToDevice(Long ruleId, Long deviceId) { // função para vincular um dispositivo a uma regra JÁ CRIADA
        // busca a regra pelo ID
        Rule rule = ruleRepository.findById(ruleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Regra não encontrada."));

        // busca o dispositivo pelo ID
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dispositivo não encontrado."));

        // instancia o novo vinculo em classe
        DeviceRule deviceRule = new DeviceRule();
        deviceRule.setRule(rule);
        deviceRule.setDevice(device);
        deviceRule.setEnabled(true);

        // salva essa classe no db
        deviceRuleRepository.save(deviceRule);
    }

    public void linkRuleToPlace(Long ruleId, Long placeId) { // função para vincular um local a uma regra JÁ CRIADA
        Rule rule = ruleRepository.findById(ruleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Regra não encontrada."));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local não encontrado."));

        PlaceRule placeRule = new PlaceRule();
        placeRule.setRule(rule);
        placeRule.setPlace(place);
        placeRule.setEnabled(true);

        placeRuleRepository.save(placeRule);
    }

    public RuleResponse createRule(RuleRequest request) {
        // regra para verificar se já existe regra com o mesmo nome criada.
        if (ruleRepository.findByNameIgnoreCase(request.name()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma regra cadastrada com este nome.");
        }

        // instancia Rule e usa os dados vindo do DTO
        Rule rule = new Rule();
        rule.setName(request.name());
        rule.setOccurrence(request.occurrence());
        rule.setSeverity(request.severity());
        rule.setApplyToAll(request.applyToAll());

        // de momento email mas é necessário issue de notificação completa para terminar
        rule.setNotificationChannel("EMAIL");

        // salva no bd
        Rule savedRule = ruleRepository.save(rule);

        // devolve a nova entidade (já instanciada na memória) no formato do DTO de resposta.
        return new RuleResponse(
                savedRule.getId(),
                savedRule.getName(),
                savedRule.getOccurrence(),
                savedRule.getSeverity(),
                savedRule.isApplyToAll()
        );
    }
}