package com.example.PFC_DAM.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "protectoras")
public class Protectora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre",
            nullable = false,
            length = 50)
    private String nombre;

    @Column(name = "direccion",
            length = 64)
    private String direccion;

    @Column(name = "telefono",
            length = 15)
    private String telefono;

    @Column(name = "provincia",
            length = 30)
    private String provincia;

    @Column(name = "email_contacto",
            length = 64)
    private String emailContacto;

    @Column(name = "logo")
    private String logo;

    @Column(columnDefinition = "TEXT")
    private String presentacion;

    //RELACIONES:
    //Con cuenta
    @OneToOne
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;

    //Con animales
    @OneToMany(mappedBy = "protectora")
    private List<Animal> animales;

    //Con red social
    @OneToMany(mappedBy = "protectora")
    private List<RedSocial> redesSociales;

}
