package com.example.PFC_DAM.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "animales")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,
            length = 30,
            name = "nombre")
    private String nombre;

    @Column(nullable = false,
            length = 20,
            name = "especie")
    private String especie;

    @Column(nullable = false,
            length = 40,
            name = "raza")
    private String raza;

    @Column(name = "sexo",
            nullable = false,
            length = 10
    )
    private String sexo;

    @Column(name = "fecha_nacimiento",
            nullable = false
    )
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_ingreso",
            nullable = false
    )
    private LocalDate fechaIngreso;

    @Column(nullable = false,
            length = 10,
            name = "estado")
    private String estado;

    @Column(length = 20,
            name = "tamano")
    private String tamano;

    @Column(nullable = false,
            scale = 2,
            precision = 5,
            name = "peso")
    private BigDecimal peso;

    @Column(name = "foto_principal",
            nullable = false)
    private String fotoPrincipal;

    @Column(nullable = false)
    private Boolean esterilizado;

    @Column(nullable = false)
    private Boolean vacunado;

    @Column(nullable = false)
    private Boolean desparasitado;

    @Column(nullable = false)
    private Boolean microchip;

    @Column(name = "necesidades_especiales",
            columnDefinition = "TEXT")
    private String necesidadesEspeciales;

    @Column(name = "nivel_actividad",
            nullable = false,
            length = 20)
    private String nivelActividad;

    @Column(name = "apto_perros",
            nullable = false,
            length = 50)
    private String aptoPerros;

    @Column(name = "apto_gatos",
            nullable = false,
            length = 50)
    private String aptoGatos;

    @Column(name = "apto_ninos",
            nullable = false,
            length = 50)
    private String aptoNinos;

    @Column(name = "miedos",
            length = 50)
    private String miedos;

    @Column(name = "descripcion",
            columnDefinition = "TEXT")
    private String descripcion;


    //RELACIONES:
    //Con protectora:
    @ManyToOne
    @JoinColumn(name = "protectora_id")
    private Protectora protectora;

    //Con favoritos:
    @OneToMany(mappedBy = "animal")
    private List<Favorito> favoritos;

    //Con solicitudes:
    @OneToMany(mappedBy = "animal")
    private List<Solicitud> solicitudes;


}
