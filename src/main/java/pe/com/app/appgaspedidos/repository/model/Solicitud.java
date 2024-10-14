package pe.com.app.appgaspedidos.repository.model;
import jakarta.persistence.*;
import pe.com.app.appgaspedidos.tranversal.enums.TipoSolicitud;

import java.time.LocalDateTime;

@Entity
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaRegistro;

    @Enumerated(EnumType.STRING)
    private TipoSolicitud tipo; // Puede ser GAS o TERMA

    private String observaciones;
    private String direccion;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;
}
