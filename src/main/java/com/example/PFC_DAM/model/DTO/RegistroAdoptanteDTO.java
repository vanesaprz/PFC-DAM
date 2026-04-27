package com.example.PFC_DAM.model.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistroAdoptanteDTO {
    //CUENTA
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 5)
    private String password;
    private String repetirPassword;

    //ADOPTANTE

    @NotBlank
    private String nombre;

    private String provincia;
    private String presentacion;
    private String foto_perfil;


}
