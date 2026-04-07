package com.example.PFC_DAM.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes")
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            name = "mensaje",
            columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "notas",
            columnDefinition = "TEXT")
    private String notas;

    @Column(name = "estado",
            columnDefinition = "default 'Pendiente'",
            nullable = false)
    private String estado;

    @Column(name = "fecha",
            nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            updatable = false)
    private LocalDateTime fecha;


    //RELACIONES:
    //Con adoptantes:
    @ManyToOne
    @JoinColumn(name = "adoptante_id")
    private Adoptante adoptante;

    //Con animal:
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
}
