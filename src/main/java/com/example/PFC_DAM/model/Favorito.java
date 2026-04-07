package com.example.PFC_DAM.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "favoritos",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"adoptante_id", "animal_id"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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