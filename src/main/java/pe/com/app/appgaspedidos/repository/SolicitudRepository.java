package pe.com.app.appgaspedidos.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import pe.com.app.appgaspedidos.repository.model.Solicitud;
import pe.com.app.appgaspedidos.repository.model.Estado;
import pe.com.app.appgaspedidos.tranversal.enums.TipoEstado;

import java.util.List;

@ApplicationScoped
public class SolicitudRepository {

    @PersistenceContext(unitName = "perciPOSTGREL")
    private EntityManager entityManager;

    @Transactional
    public void guardar(Solicitud solicitud) {
        entityManager.persist(solicitud);
    }

    public Solicitud buscarPorId(Long id) {
        return entityManager.find(Solicitud.class, id);
    }

    public List<Solicitud> listarTodos() {
        return entityManager.createQuery("SELECT s FROM Solicitud s", Solicitud.class).getResultList();
    }

    @Transactional
    public void eliminar(Long id) {
        Solicitud solicitud = buscarPorId(id);
        if (solicitud != null) {
            entityManager.remove(solicitud);
        }
    }

    // Método para buscar solicitudes por estado (Pendiente, Aprobada, Rechazada, Completada)
    public List<Solicitud> buscarPorEstado(TipoEstado tipoEstado) {
        return entityManager.createQuery("SELECT s FROM Solicitud s WHERE s.estado.estado = :tipoEstado", Solicitud.class)
                .setParameter("tipoEstado", tipoEstado)
                .getResultList();
    }

    // Método para buscar solicitudes por DNI del cliente
    public List<Solicitud> buscarPorDniCliente(String dni) {
        return entityManager.createQuery("SELECT s FROM Solicitud s WHERE s.cliente.dni = :dni", Solicitud.class)
                .setParameter("dni", dni)
                .getResultList();
    }
}
