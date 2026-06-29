package custompage.marketing.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import custompage.marketing.dto.VentaRealizadaEventDTO;
import custompage.marketing.model.Promocion;
import custompage.marketing.repository.PromocionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class SalesEventListener {

    private final PromocionRepository promocionRepository;
    private final ObjectMapper objectMapper;

    public SalesEventListener(PromocionRepository promocionRepository, ObjectMapper objectMapper) {
        this.promocionRepository = promocionRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "venta-realizada-topic", groupId = "marketing-group")
    public void escucharVentas(String mensajeVenta) {
        try {
            VentaRealizadaEventDTO evento = objectMapper.readValue(mensajeVenta, VentaRealizadaEventDTO.class);

            String codigoFidelidad = "CUPON-" + evento.getNumeroOrden();

            if (promocionRepository.findByCodigoCuponIgnoreCase(codigoFidelidad).isEmpty()) {
                Promocion cuponRegalo = Promocion.builder()
                        .nombre("Cupón de Regalo por Compra " + evento.getNumeroOrden())
                        .codigoCupon(codigoFidelidad)
                        .porcentajeDescuento(new BigDecimal("15.00")) // 15% de descuento
                        .fechaInicio(LocalDate.now())
                        .fechaFin(LocalDate.now().plusMonths(2)) // Válido por 2 meses
                        .activo(true)
                        .build();

                promocionRepository.save(cuponRegalo);
                System.out.println("======================================================");
                System.out.println("MARKETING: ¡Cupón generado con éxito! -> " + codigoFidelidad);
                System.out.println("======================================================");
            }

        } catch (Exception e) {
            System.err.println("Error procesando evento de Kafka en Marketing: " + e.getMessage());
        }
    }
}