package com.example.PFC_DAM.model.enums;

import com.example.PFC_DAM.model.Cuenta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;

    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false)
    private boolean leida = false;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();
}


