package com.example.PFC_DAM.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdoptantePerfilDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    private String apellidos;
    private String provincia;
    private String presentacion;
    private String fotoPerfil;

}
