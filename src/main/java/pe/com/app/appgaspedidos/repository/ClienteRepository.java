package pe.com.app.appgaspedidos.repository;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import pe.com.app.appgaspedidos.repository.model.Cliente;

import java.util.List;
import java.util.Optional;
@ApplicationScoped
public class ClienteRepository {

    @PersistenceContext(unitName = "perciPOSTGREL")
    private EntityManager entityManager;


    // Método para crear un nuevo cliente
    @Transactional
    public void crearCliente(Cliente cliente) {
        entityManager.persist(cliente);
    }
    public Cliente buscarPorDni(String dni) {
        try {
            return entityManager.createQuery("SELECT c FROM Cliente c WHERE c.dni = :dni", Cliente.class)
                    .setParameter("dni", dni)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Retorna null si no se encuentra el cliente o ocurre algún error
        }
    }
    // Método para obtener un cliente por ID
    public Optional<Cliente> obtenerClientePorId(Long id) {
        Cliente cliente = entityManager.find(Cliente.class, id);
        return cliente != null ? Optional.of(cliente) : Optional.empty();
    }

    // Método para obtener todos los clientes
    public List<Cliente> obtenerTodosLosClientes() {
        return entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }

    // Método para actualizar un cliente existente
    @Transactional
    public Cliente actualizarCliente(Cliente cliente) {
        return entityManager.merge(cliente);
    }

    // Método para eliminar un cliente por ID
    @Transactional
    public void eliminarCliente(Long id) {
        Cliente cliente = entityManager.find(Cliente.class, id);
        if (cliente != null) {
            entityManager.remove(cliente);
        }
    }
    // Método para verificar si un DNI ya está registrado
    public boolean existeDni(String dni) {
        Long count = entityManager.createQuery("SELECT COUNT(c) FROM Cliente c WHERE c.dni = :dni", Long.class)
                .setParameter("dni", dni)
                .getSingleResult();
        return count > 0; // Devuelve true si el DNI ya existe
    }
}
