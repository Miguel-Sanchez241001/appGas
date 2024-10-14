package pe.com.app.appgaspedidos.repository.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "dni", nullable = false, unique = true)
    private String dni;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;
    @Column(name = "correo")
    private String correo;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Solicitud> solicitudes;

}
