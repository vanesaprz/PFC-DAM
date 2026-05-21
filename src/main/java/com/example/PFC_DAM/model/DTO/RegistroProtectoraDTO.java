package com.example.PFC_DAM.model.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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


    @NotBlank(message = "El CIF/NIF es obligatorio")
    @Pattern(regexp = "^[ABCDEFGHJNPQRSUVW]\\d{7}[0-9A-J]$",
            message = "El formato del CIF/NIF no es válido")
    private String cif;


    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[6789]\\d{8}$",
            message = "El teléfono debe tener 9 dígitos y empezar por 6, 7, 8 o 9")
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
