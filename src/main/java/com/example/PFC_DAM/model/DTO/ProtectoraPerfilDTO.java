package com.example.PFC_DAM.model.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class ProtectoraPerfilDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String direccion;

    @Pattern(regexp = "^$|^[6789]\\d{8}$",
            message = "El teléfono debe tener 9 dígitos y empezar por 6, 7, 8 o 9")
    private String telefono;

    private String provincia;

    @Email(message = "El email de contacto no es válido")
    private String emailContacto;

    @URL(message = "Introduce una dirección web válida")
    private String web;

    private String presentacion;
}