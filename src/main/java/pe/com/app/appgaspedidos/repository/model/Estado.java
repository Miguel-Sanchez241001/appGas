package pe.com.app.appgaspedidos.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.app.appgaspedidos.tranversal.enums.TipoEstado;

import java.time.LocalDateTime;
@Entity
@Table(name = "estado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaRegistro;

    @Enumerated(EnumType.STRING)
    private TipoEstado estado;
}