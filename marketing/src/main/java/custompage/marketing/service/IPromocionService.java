package custompage.marketing.service;

import custompage.marketing.dto.PromocionDTO;
import java.util.List;

public interface IPromocionService {
    PromocionDTO crearPromocion(PromocionDTO dto);
    List<PromocionDTO> listarTodas();
    PromocionDTO buscarPorCupon(String codigo);
}