package com.example.PFC_DAM.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favoritos",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"adoptante_id", "animal_id"})
        })
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "adoptante_id", nullable = false)
    private Adoptante adoptante;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;


}