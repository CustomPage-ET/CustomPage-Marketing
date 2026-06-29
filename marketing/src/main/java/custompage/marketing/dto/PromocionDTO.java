package custompage.marketing.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromocionDTO {
    private Long idPromocion;

    @NotBlank(message = "El nombre de la promoción es obligatorio")
    private String nombre;

    @NotBlank(message = "El código del cupón es obligatorio")
    @Size(max = 20, message = "El cupón no puede superar los 20 caracteres")
    private String codigoCupon;

    @NotNull(message = "El porcentaje de descuento es obligatorio")
    @Min(value = 1, message = "El descuento mínimo es 1%")
    @Max(value = 100, message = "El descuento máximo es 100%")
    private BigDecimal porcentajeDescuento;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de término es obligatoria")
    private LocalDate fechaFin;

    private Boolean activo;
}