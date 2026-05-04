package com.example.PFC_DAM.model.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class RegistroProtectoraDTO {

    //CUENTA
    @Email
    @NotBlank(message = "El email es obligatorio")
    private String email;
    @NotBlank
    @Size(min = 5, message = "La contraseña debe tener al menos 5 caracteres")
    private String password;
    private String repetirPassword;

    //PROTECTORA
    @NotBlank
    private String nombre;
    @NotBlank
    private String direccion;

    @NotBlank(message = "El CIF es obligatorio")
    @Size(max = 10, message = "El CIF no puede tener más de 10 caracteres")
    private String cif;

    @Size(max = 15, message = "El teléfono es demasiado largo")
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @URL(message = "Introduce una dirección web válida")
    private String web;

    @NotBlank
    private String provincia;

    @Email
    private String emailContacto;
    private String logo;
    private String presentacion;


}
