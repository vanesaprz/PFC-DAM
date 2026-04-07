package com.example.PFC_DAM.model;

import jakarta.persistence.*;

@Entity
@Table(name = "redes_sociales")
public class RedSocial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
