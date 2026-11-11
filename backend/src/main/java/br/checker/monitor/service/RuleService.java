package br.checker.monitor.service;

import br.checker.monitor.dto.RuleRequest;
import br.checker.monitor.dto.RuleResponse;
import br.checker.monitor.model.Device;
import br.checker.monitor.model.Place;
import br.checker.monitor.model.Rule;
import br.checker.monitor.repository.DeviceRepository;
import br.checker.monitor.repository.PlaceRepository;
import br.checker.monitor.repository.RuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RuleService {

    private final RuleRepository ruleRepository;
    private final DeviceRepository deviceRepository;
    private final PlaceRepository placeRepository;

    // é como se fosse o get_db do FastAPI, no momento do springboot instanciar na memória as conexões necessarias com as tabelas necessarias, são passada as que realmente são necessárias:
    public RuleService(RuleRepository ruleRepository, DeviceRepository deviceRepository, PlaceRepository placeRepository) {
        this.ruleRepository = ruleRepository;
        this.deviceRepository = deviceRepository;
        this.placeRepository = placeRepository;
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

        // caso seja atribuido a um dispositivo no momento de sua criação, busca o ID e vincula na table.
        if (request.deviceId() != null) {
            Device device = deviceRepository.findById(request.deviceId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dispositivo não encontrado."));
            rule.setDevice(device);
        }

        // caso seja atribuido a um local no momento de sua criação, busca o ID e vincula na table.
        if (request.placeId() != null) {
            Place place = placeRepository.findById(request.placeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local não encontrado."));
            rule.setPlace(place);
        }

        // salva no bd
        Rule savedRule = ruleRepository.save(rule);

        // devolve a nova entidade (já instanciada na memória) no formato do DTO de resposta.
        return new RuleResponse(
                savedRule.getId(),
                savedRule.getName(),
                savedRule.getOccurrence(),
                savedRule.getSeverity(),
                savedRule.isApplyToAll(),
                savedRule.getDevice() != null ? savedRule.getDevice().getId() : null,
                savedRule.getPlace() != null ? savedRule.getPlace().getId() : null
        );
    }
}