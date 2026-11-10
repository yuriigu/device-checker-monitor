package br.checker.monitor.controller;

import br.checker.monitor.dto.PlaceAddressResponse;
import br.checker.monitor.dto.PlaceRequest;
import br.checker.monitor.dto.PlaceResponse;
import br.checker.monitor.model.Place;
import br.checker.monitor.service.PlaceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para gerenciamento de lugares (places).
 *
 * <p>Endpoints:
 *   GET    /api/places                    - lista todos os lugares
 *   GET    /api/places/{id}               - busca um lugar pelo ID
 *   POST   /api/places                    - cadastra um novo lugar
 *   PUT    /api/places/{id}               - atualiza um lugar existente
 *   DELETE /api/places/{id}               - remove um lugar
 *   POST   /api/places/{id}/validate-address - valida se o lugar pode ter endereco (regra de negocio)
 *
 * <p>Anotacao {@link RestController}: controller REST que retorna JSON.
 * Anotacao {@link RequestMapping("/api/places")}: caminho base dos endpoints.
 */
@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    /**
     * Lista todos os lugares cadastrados.
     */
    @GetMapping
    public ResponseEntity<List<PlaceResponse>> listPlaces() {
        List<Place> places = placeService.findAll();
        List<PlaceResponse> responses = places.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Busca um lugar pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlaceResponse> getPlace(@PathVariable Long id) {
        Place place = placeService.findById(id);
        return ResponseEntity.ok(toResponse(place));
    }

    /**
     * Cadastra um novo lugar.
     */
    @PostMapping
    public ResponseEntity<PlaceResponse> createPlace(@Valid @RequestBody PlaceRequest request) {
        Place place = placeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(place));
    }

    /**
     * Atualiza um lugar existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlaceResponse> updatePlace(@PathVariable Long id,
                                                     @Valid @RequestBody PlaceRequest request) {
        Place place = placeService.update(id, request);
        return ResponseEntity.ok(toResponse(place));
    }

    /**
     * Remove um lugar pelo ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        placeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Valida se um lugar pode ter endereco fisico (apenas lugares raiz).
     * Lança BusinessRuleException se o lugar nao for raiz.
     */
    @PostMapping("/{id}/validate-address")
    public ResponseEntity<Void> validateAddress(@PathVariable Long id) {
        placeService.validateAddressRule(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Converte a entidade Place para o DTO PlaceResponse.
     */
    private PlaceResponse toResponse(Place p) {
        PlaceAddressResponse addressResponse = p.getAddress() != null
                ? new PlaceAddressResponse(
                p.getAddress().getStreet(),
                p.getAddress().getNumber(),
                p.getAddress().getCity(),
                p.getAddress().getState(),
                p.getAddress().getZipCode())
                : null;

        return new PlaceResponse(
                p.getId(),
                p.getName(),
                p.getParent() != null ? p.getParent().getId() : null,
                p.getCidr(),
                addressResponse
        );
    }
}