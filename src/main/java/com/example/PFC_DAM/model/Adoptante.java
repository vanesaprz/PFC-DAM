package com.example.PFC_DAM.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "adoptantes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @OneToMany(mappedBy = "adoptante")
    private List<Favorito> favoritos;

    //Con solicitudes:
    @OneToMany(mappedBy = "adoptante")
    private List<Solicitud> solicitudes;


}
