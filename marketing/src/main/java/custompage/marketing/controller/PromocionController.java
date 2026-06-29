package custompage.marketing.controller;

import custompage.marketing.dto.PromocionDTO;
import custompage.marketing.service.IPromocionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marketing/promociones")
public class PromocionController {

    private final IPromocionService service;
    public PromocionController(IPromocionService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<PromocionDTO> crear(@Validated @RequestBody PromocionDTO dto) {
        return ResponseEntity.ok(service.crearPromocion(dto));
    }

    @GetMapping
    public ResponseEntity<List<PromocionDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/validar/{cupon}")
    public ResponseEntity<PromocionDTO> obtenerPorCupon(@PathVariable String cupon) {
        return ResponseEntity.ok(service.buscarPorCupon(cupon));
    }
}