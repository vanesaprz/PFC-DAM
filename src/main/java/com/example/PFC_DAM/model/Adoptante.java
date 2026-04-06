package com.example.PFC_DAM.model;

import jakarta.persistence.*;

@Entity
@Table(name = "adoptantes")
public class Adoptante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre",
            nullable = false,
            length = 50)
    private String nombre;

    @Column(name = "apellidos",
            length = 64)
    private String apellidos;

    @Column(name = "provincia",
            length = 30)
    private String provincia;

    @Column(columnDefinition = "TEXT")
    private String presentacion;

    @Column(name = "foto_perfil")
    private String fotoPerfil;
    //RELACIONES:
    //Con cuenta
    @OneToOne
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;
    //Con favoritos

    //Con solicitudes


    //CONSTRUCTORES:

    public Adoptante() {
    }

    public Adoptante(String nombre, String apellidos, String provincia, String presentacion, String fotoPerfil) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.provincia = provincia;
        this.presentacion = presentacion;
        this.fotoPerfil = fotoPerfil;
    }

    //Setters y getters


    @Override
    public String toString() {
        return "adoptante{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", provincia='" + provincia + '\'' +
                ", presentacion='" + presentacion + '\'' +
                ", fotoPerfil='" + fotoPerfil + '\'' +
                '}';
    }
}
