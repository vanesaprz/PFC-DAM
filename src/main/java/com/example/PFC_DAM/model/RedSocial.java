package com.example.PFC_DAM.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "redes_sociales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RedSocial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo",
            nullable = false,
            length = 20)
    private String tipo;

    @Column(name = "enlace_usuario",
            nullable = false)
    private String enlaceUsuario;

    //RELACIÓN CON PROTECTORA
    @ManyToOne
    @JoinColumn(name = "protectora_id")
    private Protectora protectora;

}
