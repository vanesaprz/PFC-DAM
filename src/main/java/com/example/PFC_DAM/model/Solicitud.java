package com.example.PFC_DAM.model;

import com.example.PFC_DAM.model.enums.EstadoSolicitud;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
            nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

    @Column(name = "fecha",
            nullable = false,
            updatable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(name = "tipo_vivienda", nullable = false)
    private String tipoVivienda;
    
    @Column(name = "otros_animales", nullable = false)
    private String otrosAnimales;


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
