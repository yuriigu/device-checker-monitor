package br.checker.monitor.service;

import br.checker.monitor.exception.BusinessRuleException;
import br.checker.monitor.exception.ResourceNotFoundException;
import br.checker.monitor.dto.PlaceRequest;
import br.checker.monitor.model.Place;
import br.checker.monitor.repository.PlaceRepository;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servico de lugares (places).
 *
 * <p>Aplica a regra de negocio: somente lugares "raiz" (parent_id == null)
 * podem ter um endereco fisico cadastrado em PlaceAddress.
 *
 * <p>Anotacao {@link Service}: indica ao Spring que esta e uma camada de servico (business logic).
 * O Spring cria um bean singleton gerenciado pelo contêiner IoC.
 *
 * <p>Anotacao {@link Transactional}: garante que cada metodo publico rode dentro de uma
 * transacao do banco. Se houver excecao, a transacao e desfeita (rollback).
 */
@Service
@Transactional
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    /**
     * Busca um lugar pelo ID ou lança ResourceNotFoundException se nao existir.
     */
    public Place findById(Long id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lugar nao encontrado com id: " + id));
    }

    /**
     * Verifica se o lugar e raiz (nao tem lugar pai).
     * Apenas lugares raiz podem ter endereco fisico.
     */
    public void validateAddressRule(Long placeId) {
        Place place = findById(placeId);
        if (!place.isRoot()) {
            Long parentId = place.getParent() != null ? place.getParent().getId() : null;
            throw new BusinessRuleException(
                    "Somente lugares raiz (sem lugar pai) podem ter endereco fisico. " +
                            "O lugar id=" + placeId + " tem parent_id=" + parentId);
        }
    }

    /**
     * Cadastra um novo lugar.
     */
    public Place create(PlaceRequest request) {
        Place place = new Place();
        place.setName(request.name());

        if (request.parentId() != null) {
            Place parent = findById(request.parentId());
            place.setParent(parent);
        }

        return placeRepository.save(place);
    }

    /**
     * Atualiza um lugar existente.
     */
    public Place update(Long id, PlaceRequest request) {
        Place place = findById(id);
        place.setName(request.name());

        if (request.parentId() != null) {
            Place parent = findById(request.parentId());
            place.setParent(parent);
        } else {
            place.setParent(null);
        }

        return placeRepository.save(place);
    }

    /**
     * Lista todos os lugares cadastrados.
     */
    @Transactional(readOnly = true)
    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    /**
     * Remove um lugar pelo ID.
     */
    public void delete(Long id) {
        if (!placeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lugar nao encontrado com id: " + id);
        }
        placeRepository.deleteById(id);
    }
}