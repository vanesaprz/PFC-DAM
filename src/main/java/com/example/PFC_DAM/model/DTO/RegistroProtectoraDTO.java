package com.example.PFC_DAM.model.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistroProtectoraDTO {

    //CUENTA
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 5)
    private String password;
    private String repetirPassword;

    //PROTECTORA
    @NotBlank
    private String nombre;
    @NotBlank
    private String direccion;

    @NotBlank
    @Size(min = 9)
    private String cif;

    @Size(min = 9)
    @NotBlank
    private String telefono;

    @NotBlank
    private String provincia;

    @Email
    private String email_contacto;
    private String logo;
    private String presentacion;


}
