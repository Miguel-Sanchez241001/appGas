package pe.com.app.appgaspedidos.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.app.appgaspedidos.tranversal.enums.TipoEstado;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "estado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private TipoEstado estado;

    // Método para obtener la fecha formateada
    public String getFechaRegistroFormatted() {
        return fechaRegistro != null ? fechaRegistro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }
    public Estado(TipoEstado estado){
        this.estado = estado;
    }
    @ManyToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private Solicitud solicitud;



    public Estado(TipoEstado estado, Solicitud solicitud) {
        this.estado = estado;
        this.solicitud = solicitud;
        this.fechaRegistro = LocalDateTime.now(); // La fecha de registro es la actual
    }
}
