package com.example.PFC_DAM.model.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistroAdoptanteDTO {
    //CUENTA
    @Email
    @NotBlank(message = "El email es obligatorio")
    private String email;
    @NotBlank
    @Size(min = 5, message = "La contraseña debe tener al menos 5 caracteres")
    private String password;
    private String repetirPassword;

    //ADOPTANTE
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    private String apellidos;
    private String provincia;
    private String presentacion;
    private String foto_perfil;


}
