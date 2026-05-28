package com.example.PFC_DAM.model;

import com.example.PFC_DAM.model.enums.Especie;
import com.example.PFC_DAM.model.enums.EstadoAnimal;
import com.example.PFC_DAM.model.enums.Sexo;
import com.example.PFC_DAM.model.enums.Tamano;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Table(name = "animales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,
            length = 30,
            name = "nombre")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Enumerated(EnumType.STRING)
    private Especie especie;

    @Column(nullable = false,
            length = 40,
            name = "raza")
    @NotBlank(message = "Es necesario indicar la raza")
    private String raza;

    @Column(name = "sexo",
            nullable = false,
            length = 10
    )
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Column(name = "fecha_nacimiento",
            nullable = false
    )
    @NotNull(message = "Indica la fecha de nacimiento aproximada")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_ingreso",
            nullable = false
    )
    @NotNull(message = "La fecha de ingreso en la protectora es obligatoria")
    private LocalDate fechaIngreso;

    @Column(nullable = false,
            length = 10,
            name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoAnimal estado;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Tamano tamano;

    @Column(nullable = false,
            scale = 2,
            precision = 5,
            name = "peso")
    @NotNull(message = "Debes indicar el peso")
    @DecimalMin(value = "0.1", message = "El peso debe ser mayor a 0")
    private BigDecimal peso;

    @NotBlank(message = "La URL de la foto es obligatoria")
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
    private Boolean aptoPerros;

    @Column(name = "apto_gatos",
            nullable = false)
    private Boolean aptoGatos;

    @Column(name = "apto_ninos",
            nullable = false)
    private Boolean aptoNinos;

    @Column(name = "miedos",
            length = 50)
    private String miedos;

    @Column(name = "descripcion",
            columnDefinition = "TEXT")
    private String descripcion;


    @Transient
    public String getEdadFormateada() {
        String edad = "Desconocida";
        if (this.fechaNacimiento != null) {
            Period periodo = Period.between(this.fechaNacimiento, LocalDate.now());
            if (periodo.getYears() > 0) {
                edad = periodo.getYears() + (periodo.getYears() == 1 ? " año" : " años");
            } else {
                edad = periodo.getMonths() + (periodo.getMonths() == 1 ? " mes" : " meses");
            }
        }
        return edad;
    }

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

