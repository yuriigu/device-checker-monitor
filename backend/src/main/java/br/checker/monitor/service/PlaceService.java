package br.checker.monitor.service;

import br.checker.monitor.dto.PlaceAddressRequest;
import br.checker.monitor.exception.BusinessRuleException;
import br.checker.monitor.exception.ResourceNotFoundException;
import br.checker.monitor.dto.PlaceRequest;
import br.checker.monitor.model.Place;
import br.checker.monitor.model.PlaceAddress;
import br.checker.monitor.repository.DeviceRepository;
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
    private final DeviceRepository deviceRepository;

    public PlaceService(PlaceRepository placeRepository, DeviceRepository deviceRepository) {
        this.placeRepository = placeRepository;
        this.deviceRepository = deviceRepository;
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
     * Verifica se o nome do local já está cadastrado no sistema.
     */
    private void validateNameAvailable(String name, Long currentId) {
        String trimmed = name.trim();
        boolean taken = currentId == null
                ? placeRepository.existsByNameIgnoreCase(trimmed)
                : placeRepository.existsByNameIgnoreCaseAndIdNot(trimmed, currentId);
        if (taken) {
            throw new BusinessRuleException("Ja existe um local com o nome: " + trimmed);
        }
    }

    /**
     * Valida se há referência circular na hierarquia.
     */
    private void validateNoCycle(Place place, Place newParent) {
        Place current = newParent;
        while (current != null) {
            if (current.getId().equals(place.getId())) {
                throw new BusinessRuleException(
                        "Referencia circular: o local nao pode ser pai de si mesmo ou de um descendente.");
            }
            current = current.getParent();
        }
    }

    /**
     * Aplica e valida o pai e o endereço do local.
     */
    private void applyParentAndAddress(Place place, PlaceRequest request) {
        PlaceAddressRequest a = request.address();
        if (request.parentId() != null) {
            if (a != null) {
                throw new BusinessRuleException("Somente lugares raiz (sem lugar pai) podem ter endereco fisico.");
            }
            Place parent = findById(request.parentId());
            validateNoCycle(place, parent);
            place.setParent(parent);
            place.setAddress(null);
        } else {
            if (a == null) {
                throw new BusinessRuleException("Lugares raiz precisam de endereco.");
            }
            place.setParent(null);
            PlaceAddress address = place.getAddress();
            if (address == null) {
                address = new PlaceAddress();
                address.setPlace(place);
                place.setAddress(address);
            }
            address.setStreet(a.street());
            address.setNumber(a.number());
            address.setCity(a.city());
            address.setState(a.state());
            address.setZipCode(a.zipCode());
        }
    }

    /**
     * Cadastra um novo lugar.
     */
    public Place create(PlaceRequest request) {
        validateNameAvailable(request.name(), null);

        Place place = new Place();

        place.setName(request.name().trim());
        place.setCidr(request.cidr());

        applyParentAndAddress(place, request);

        return placeRepository.save(place);
    }

    /**
     * Atualiza um lugar existente.
     */
    public Place update(Long id, PlaceRequest request) {
        Place place = findById(id);

        validateNameAvailable(request.name(), id);
        place.setName(request.name().trim());
        place.setCidr(request.cidr());

        applyParentAndAddress(place, request);

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
        if (placeRepository.existsByParentId(id)) {
            throw new BusinessRuleException("Nao e possivel remover um local que possui locais filhos.");
        }
        if (deviceRepository.existsByPlaceId(id)) {
            throw new BusinessRuleException("Nao e possivel remover um local que possui dispositivos vinculados.");
        }
        placeRepository.deleteById(id);
    }
}