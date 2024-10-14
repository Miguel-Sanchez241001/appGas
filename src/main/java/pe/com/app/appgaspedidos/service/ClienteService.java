package pe.com.app.appgaspedidos.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pe.com.app.appgaspedidos.repository.ClienteRepository;
import pe.com.app.appgaspedidos.repository.model.Cliente;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClienteService {

    @Inject
    private ClienteRepository clienteRepository;

    // Método para crear un nuevo cliente
    @Transactional
    public void crearCliente(Cliente cliente) {
        clienteRepository.crearCliente(cliente);
    }

    // Método para obtener un cliente por ID
    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.obtenerClientePorId(id);
    }

    // Método para obtener todos los clientes
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.obtenerTodosLosClientes();
    }

    // Método para actualizar un cliente
    @Transactional
    public void actualizarCliente(Cliente cliente) {
        clienteRepository.actualizarCliente(cliente);
    }

    // Método para eliminar un cliente
    @Transactional
    public void eliminarCliente(Long id) {
        clienteRepository.eliminarCliente(id);
    }

    public boolean existeDni(String dni) {
        return clienteRepository.existeDni(dni);
    }
}

