package com.example.PFC_DAM.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cuentas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email",
            nullable = false,
            unique = true,
            length = 64)
    private String email;

    @Column(name = "contrasena",
            nullable = false)
    private String contrasena;

    @Column(name = "rol",
            nullable = false,
            length = 20)
    private String rol;

    //RELACIONES:

    //Con adoptante
    @OneToOne(mappedBy = "cuenta")
    private Adoptante adoptante;
    //Con protectora
    @OneToOne(mappedBy = "cuenta")
    private Protectora protectora;
    
}
