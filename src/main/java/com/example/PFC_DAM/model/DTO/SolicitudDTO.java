package com.example.PFC_DAM.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SolicitudDTO {

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;

    @NotBlank(message = "Debes indicar el tipo de vivienda")
    private String tipoVivienda;

    @NotBlank(message = "Debes indicar si convives con otros animales")
    private String otrosAnimales;

}
