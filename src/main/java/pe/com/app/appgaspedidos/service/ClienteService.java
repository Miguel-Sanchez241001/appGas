package pe.com.app.appgaspedidos.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import pe.com.app.appgaspedidos.repository.ClienteRepository;
import pe.com.app.appgaspedidos.repository.model.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ClienteService {

    @Inject
    private ClienteRepository clienteRepository;
    @Transactional
    public void insertarClientesSiVacia() {
        List<Cliente> clientesExistentes = clienteRepository.obtenerTodosLosClientes();

        // Verificamos si la lista de clientes existentes está vacía
        if (clientesExistentes.isEmpty()) {
            for (int i = 1; i <= 100; i++) {
                // Generar un DNI único usando UUID (recortamos a 8 caracteres)
                String dni = UUID.randomUUID().toString().replaceAll("[^\\d]", "").substring(0, 8);

                // Generar un teléfono aleatorio que comience con '9'
                String telefono = "9" + RandomStringUtils.randomNumeric(8); // Teléfono aleatorio de 9 dígitos

                // Generar nombres y apellidos aleatorios (5 a 10 caracteres)
                String nombre = RandomStringUtils.randomAlphabetic(5, 10);
                String apellido = RandomStringUtils.randomAlphabetic(5, 10);

                // Generar una dirección aleatoria (10 a 20 caracteres)
                String direccion = RandomStringUtils.randomAlphanumeric(10, 20);

                // Generar un correo aleatorio usando UUID (único)
                String correo = "cliente" + UUID.randomUUID().toString().substring(0, 5) + "@mail.com";

                // Crear un cliente con los datos generados aleatoriamente
                Cliente cliente = new Cliente(
                        null, // ID generado automáticamente
                        nombre, // Nombre aleatorio
                        apellido, // Apellido aleatorio
                        dni, // DNI único generado con UUID
                        direccion, // Dirección aleatoria
                        telefono, // Teléfono aleatorio
                        correo, // Correo único generado con UUID
                        null // Lista de solicitudes vacía
                );

                // Guardar el cliente en la base de datos
                clienteRepository.crearCliente(cliente);
            }
            System.out.println("100 clientes han sido registrados.");
        } else {
            System.out.println("Ya existen clientes en la base de datos.");
        }
    }
    // Método para crear un nuevo cliente
    @Transactional
    public void crearCliente(Cliente cliente) {
        clienteRepository.crearCliente(cliente);
    }

    // Método para obtener un cliente por ID
    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.obtenerClientePorId(id);
    }
    // Método para buscar un cliente por DNI
    public Cliente buscarPorDni(String dni) {
        return clienteRepository.buscarPorDni(dni);
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

