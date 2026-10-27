package br.checker.monitor.controller;

import br.checker.monitor.dto.BrandResponse;
import br.checker.monitor.service.CatalogService;
import br.checker.monitor.model.DeviceModel;
import br.checker.monitor.model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para o catalogo de dados de referencia (Marcas, Modelos e Roles).
 *
 * <p>Endpoints:
 *   GET   /api/catalog/brands      - lista todas as marcas
 *   GET   /api/catalog/models      - lista todos os modelos
 *   GET   /api/catalog/roles       - lista todas as roles
 *
 * <p>Anotacao {@link RestController}: indica que esta classe e um controller REST cujos
 * metodos retornam dados serializados automaticamente para JSON (via @ResponseBody implicito).
 *
 * <p>Anotacao {@link RequestMapping("/api/catalog")}: define o caminho base de todos os
 * endpoints deste controller.
 */
@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    /**
     * Lista todas as marcas cadastradas.
     */
    @GetMapping("/brands")
    public ResponseEntity<List<BrandResponse>> listBrands() {
        return ResponseEntity.ok(catalogService.findAllBrands());
    }

    /**
     * Lista todos os modelos de dispositivos cadastrados.
     */
    @GetMapping("/models")
    public ResponseEntity<List<DeviceModel>> listModels() {
        return ResponseEntity.ok(catalogService.findAllModels());
    }

    /**
     * Lista todas as roles (perfis de acesso) cadastradas.
     */
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> listRoles() {
        return ResponseEntity.ok(catalogService.findAllRoles());
    }
}