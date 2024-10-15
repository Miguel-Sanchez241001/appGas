package pe.com.app.appgaspedidos.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import pe.com.app.appgaspedidos.tranversal.enums.TipoSolicitud;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "solicitudes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_registro", nullable = false)
    @NotNull(message = "La fecha de registro no puede ser nula")
    private LocalDateTime fechaRegistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_solicitud", nullable = false)
    @NotNull(message = "El tipo de solicitud no puede ser nulo")
    private TipoSolicitud tipo; // Puede ser GAS o TERMA

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "direccion", nullable = false)
    @NotNull(message = "La dirección no puede ser nula")
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "El cliente no puede ser nulo")
    private Cliente cliente;


    @Transient // Este campo no se persiste en la base de datos
    private Estado estadoReciente;
    public Estado getEstadoReciente() {
        if (estadoReciente == null && estados != null && !estados.isEmpty()) {
            // Obtenemos el estado más reciente de la lista de estados
            estadoReciente = estados.stream()
                    .max(Comparator.comparing(Estado::getFechaRegistro))
                    .orElse(null); // Si no hay estados, retorna null
        }
        return estadoReciente;
    }
    @Column(name = "usuario_registro", nullable = false)
    @NotNull(message = "El usuario de registro no puede ser nulo")
    private String usuarioRegistro;
    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Estado> estados; // Lista de estados asociados con la solicitud

    public String getFechaRegistroFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.fechaRegistro != null ? this.fechaRegistro.format(formatter) : null;
    }
    public void addEstado(Estado estado) {
        this.estados.add(estado); // Añades el estado a la lista
        estado.setSolicitud(this); // Actualizas la solicitud en el estado
    }

}
