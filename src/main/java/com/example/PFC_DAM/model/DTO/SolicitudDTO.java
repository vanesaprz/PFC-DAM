package com.example.PFC_DAM.model.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SolicitudDTO {

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;

    @NotBlank(message = "Debes indicar el tipo de vivienda")
    private String tipoVivienda;

    @NotBlank(message = "Debes indicar si convives con otros animales")
    private String otrosAnimales;

    @NotBlank(message = "El teléfono de contacto es obligatorio")
    @Pattern(regexp = "^[6789]\\d{8}$",
            message = "El teléfono debe tener 9 dígitos y empezar por 6, 7, 8 o 9")
    private String telefono;

}
