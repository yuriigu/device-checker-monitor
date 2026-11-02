package br.checker.monitor.controller;

import br.checker.monitor.dto.DeviceDetailResponse;
import br.checker.monitor.dto.DeviceRequest;
import br.checker.monitor.dto.DeviceSummaryResponse;
import br.checker.monitor.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de dispositivos (Device) - HU001.
 *
 * <p>Endpoints implementados:
 *   POST   /api/devices                    - cadastra um novo dispositivo
 *   GET    /api/devices                    - lista todos os dispositivos (resumido)
 *   GET    /api/devices/{id}               - busca um dispositivo detalhado pelo ID
 *   PUT    /api/devices/{id}               - atualiza um dispositivo existente
 *   DELETE /api/devices/{id}               - remove um dispositivo
 *
 * <p>Anotacoes principais:
 * <ul>
 *   <li>{@link RestController}: indica que esta classe e um controller REST.
 *       Os metodos retornam objetos que sao serializados automaticamente para JSON.</li>
 *   <li>{@link RequestMapping("/api/devices")}: define o caminho base de todos os endpoints.</li>
 *   <li>{@link PostMapping}, {@link GetMapping}, {@link PutMapping}, {@link DeleteMapping}:
 *       mapeiam metodos HTTP para metodos Java.</li>
 *   <li>{@link PathVariable}: extrai um valor do caminho da URL (ex: /devices/{id}).</li>
 *   <li>{@link RequestBody}: desserializa o corpo JSON da requisicao para um objeto Java (DTO).</li>
 *   <li>{@link Valid}: dispara a validacao Bean Validation (@NotBlank, @NotNull, etc.)
 *       no DTO recebido. Se falhar, lanca MethodArgumentNotValidException,
 *       capturada pelo GlobalExceptionHandler.</li>
 *   <li>{@link ResponseEntity}: permite controlar o status HTTP da resposta
 *       (200 OK, 201 Created, 204 No Content, 400 Bad Request, 404 Not Found, etc.).</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * Cadastra um novo dispositivo.
     *
     * Regras aplicadas pelo DeviceService:
     * <ul>
     *   <li>IP deve ser unico no banco.</li>
     *   <li>Marca deve existir.</li>
     *   <li>Modelo (se informado) deve pertencer a marca informada.</li>
     *   <li>Pelo menos uma role deve ser vinculada.</li>
     *   <li>MAC Address e normalizado antes de salvar.</li>
     * </ul>
     *
     * @param request DTO de entrada validado com @Valid
     * @return ResponseEntity com HTTP 201 Created e o dispositivo cadastrado
     */
    @PostMapping
    public ResponseEntity<DeviceDetailResponse> createDevice(@Valid @RequestBody DeviceRequest request) {
        DeviceDetailResponse created = deviceService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Lista todos os dispositivos (versao resumida para tabela/grid).
     */
    @GetMapping
    public ResponseEntity<List<DeviceSummaryResponse>> listDevices() {
        List<DeviceSummaryResponse> devices = deviceService.findAll();
        return ResponseEntity.ok(devices);
    }

    /**
     * Busca um dispositivo detalhado pelo ID.
     *
     * @param id ID do dispositivo
     * @return ResponseEntity com HTTP 200 OK e os detalhes do dispositivo,
     *         ou HTTP 404 se nao encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDetailResponse> getDevice(@PathVariable Long id) {
        DeviceDetailResponse device = deviceService.findById(id);
        return ResponseEntity.ok(device);
    }

    /**
     * Atualiza um dispositivo existente.
     *
     * Aplica as mesmas regras de validacao do cadastro (IP unico, modelo x marca, roles).
     */
    @PutMapping("/{id}")
    public ResponseEntity<DeviceDetailResponse> updateDevice(@PathVariable Long id,
                                                              @Valid @RequestBody DeviceRequest request) {
        DeviceDetailResponse updated = deviceService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Remove um dispositivo pelo ID.
     *
     * @return HTTP 204 No Content se removido com sucesso,
     *         ou HTTP 404 se nao encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}