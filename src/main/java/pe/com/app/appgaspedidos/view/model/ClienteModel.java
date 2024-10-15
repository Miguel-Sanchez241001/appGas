package pe.com.app.appgaspedidos.view.model;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.com.app.appgaspedidos.repository.model.Cliente;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteModel {
    private String nombre;
    private String apellidos;
    private String dni;
    private String direccion;
    private String telefono;
    private String correo;
    private boolean existeDni;
    private List<Cliente> clientes ;
    private List<Cliente> clientesFiltrados ;
    private  Cliente  seleccionadoCliente ;

}
