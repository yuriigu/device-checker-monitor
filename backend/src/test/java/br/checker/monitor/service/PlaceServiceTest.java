package br.checker.monitor.service;

import br.checker.monitor.dto.PlaceAddressRequest;
import br.checker.monitor.dto.PlaceRequest;
import br.checker.monitor.exception.BusinessRuleException;
import br.checker.monitor.model.Place;
import br.checker.monitor.repository.DeviceRepository;
import br.checker.monitor.repository.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private PlaceService placeService;

    private static final String CIDR = "10.0.0.0/16";
    private static final PlaceAddressRequest ADDRESS =
            new PlaceAddressRequest("Rua A", "10", "Curitiba", "PR", "80000-000");

    private Place place(Long id, Place parent) {
        Place p = new Place();
        p.setId(id);
        p.setName("local-" + id);
        p.setParent(parent);
        return p;
    }

    @Test
    void create_nomeJaExistente_lancaBusinessRuleException() {
        when(placeRepository.existsByNameIgnoreCase("Matriz")).thenReturn(true);
        PlaceRequest request = new PlaceRequest("Matriz", null, CIDR, ADDRESS);

        assertThrows(BusinessRuleException.class, () -> placeService.create(request));

        verify(placeRepository, never()).save(any());
    }

    @Test
    void create_raizSemEndereco_lancaBusinessRuleException() {
        PlaceRequest request = new PlaceRequest("Matriz", null, CIDR, null);

        assertThrows(BusinessRuleException.class, () -> placeService.create(request));

        verify(placeRepository, never()).save(any());
    }

    @Test
    void create_filhoComEndereco_lancaBusinessRuleException() {
        PlaceRequest request = new PlaceRequest("Sala", 1L, CIDR, ADDRESS);

        assertThrows(BusinessRuleException.class, () -> placeService.create(request));

        verify(placeRepository, never()).save(any());
    }

    @Test
    void update_referenciaCircular_lancaBusinessRuleException() {
        Place a = place(1L, null);
        Place b = place(2L, a);
        when(placeRepository.findById(1L)).thenReturn(Optional.of(a));
        when(placeRepository.findById(2L)).thenReturn(Optional.of(b));
        PlaceRequest request = new PlaceRequest("A", 2L, CIDR, null);

        assertThrows(BusinessRuleException.class, () -> placeService.update(1L, request));

        verify(placeRepository, never()).save(any());
    }

    @Test
    void update_localComoPaiDeSiMesmo_lancaBusinessRuleException() {
        Place a = place(1L, null);
        when(placeRepository.findById(1L)).thenReturn(Optional.of(a));
        PlaceRequest request = new PlaceRequest("A", 1L, CIDR, null);

        assertThrows(BusinessRuleException.class, () -> placeService.update(1L, request));

        verify(placeRepository, never()).save(any());
    }

    @Test
    void create_raizValida_salvaComCidrEEndereco() {
        when(placeRepository.save(any(Place.class))).thenAnswer(inv -> inv.getArgument(0));
        PlaceRequest request = new PlaceRequest("Matriz", null, CIDR, ADDRESS);

        placeService.create(request);

        ArgumentCaptor<Place> captor = ArgumentCaptor.forClass(Place.class);
        verify(placeRepository).save(captor.capture());
        Place saved = captor.getValue();
        assertEquals("Matriz", saved.getName());
        assertEquals(CIDR, saved.getCidr());
        assertNotNull(saved.getAddress());
        assertEquals("Curitiba", saved.getAddress().getCity());
        assertSame(saved, saved.getAddress().getPlace());
    }

    @Test
    void delete_comFilhos_lancaBusinessRuleException() {
        when(placeRepository.existsById(1L)).thenReturn(true);
        when(placeRepository.existsByParentId(1L)).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> placeService.delete(1L));

        verify(placeRepository, never()).deleteById(any());
    }

    @Test
    void delete_comDispositivos_lancaBusinessRuleException() {
        when(placeRepository.existsById(1L)).thenReturn(true);
        when(deviceRepository.existsByPlaceId(1L)).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> placeService.delete(1L));

        verify(placeRepository, never()).deleteById(any());
    }
}