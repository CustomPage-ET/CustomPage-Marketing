package custompage.marketing.service;

import custompage.marketing.dto.PromocionDTO;
import custompage.marketing.exception.ResourceNotFoundException;
import custompage.marketing.model.Promocion;
import custompage.marketing.repository.PromocionRepository;
import custompage.marketing.service.IPromocionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromocionServiceImpl implements IPromocionService {

    private final PromocionRepository repo;
    public PromocionServiceImpl(PromocionRepository repo) { this.repo = repo; }

    @Override
    public PromocionDTO crearPromocion(PromocionDTO dto) {
        if(repo.findByCodigoCuponIgnoreCase(dto.getCodigoCupon()).isPresent()){
            throw new RuntimeException("El código de cupón ya existe en el sistema");
        }

        Promocion p = Promocion.builder()
                .nombre(dto.getNombre())
                .codigoCupon(dto.getCodigoCupon().toUpperCase())
                .porcentajeDescuento(dto.getPorcentajeDescuento())
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .activo(dto.getActivo() != null ? dto.getActivo() : true)
                .build();

        p = repo.save(p);
        dto.setIdPromocion(p.getIdPromocion());
        return dto;
    }

    @Override
    public List<PromocionDTO> listarTodas() {
        return repo.findAll().stream().map(p -> PromocionDTO.builder()
                .idPromocion(p.getIdPromocion())
                .nombre(p.getNombre())
                .codigoCupon(p.getCodigoCupon())
                .porcentajeDescuento(p.getPorcentajeDescuento())
                .fechaInicio(p.getFechaInicio())
                .fechaFin(p.getFechaFin())
                .activo(p.getActivo())
                .build()).collect(Collectors.toList());
    }

    @Override
    public PromocionDTO buscarPorCupon(String codigo) {
        Promocion p = repo.findByCodigoCuponIgnoreCase(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Cupón de descuento no encontrado o inválido"));

        return PromocionDTO.builder()
                .idPromocion(p.getIdPromocion())
                .nombre(p.getNombre())
                .codigoCupon(p.getCodigoCupon())
                .porcentajeDescuento(p.getPorcentajeDescuento())
                .fechaInicio(p.getFechaInicio())
                .fechaFin(p.getFechaFin())
                .activo(p.getActivo())
                .build();
    }
}