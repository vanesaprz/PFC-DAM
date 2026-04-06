package com.example.PFC_DAM.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email",
            nullable = false,
            unique = true,
            length = 64)
    private String email;

    @Column(name = "contrasena",
            nullable = false)
    private String contrasena;

    @Column(name = "rol",
            nullable = false,
            length = 20)
    private String rol;

    //RELACIONES:

    //Con adoptante
    @OneToOne(mappedBy = "adoptante")
    private Adoptante adoptante;
    //Con protectora
    @OneToOne(mappedBy = "protectora")
    private Protectora protectora;

    //CONSTRUCTORES:


    public Cuenta() {
    }

    public Cuenta(String email, String contrasena, String rol) {
    }

    //Setters y getters


}
