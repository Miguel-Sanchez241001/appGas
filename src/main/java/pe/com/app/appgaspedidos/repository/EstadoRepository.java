package pe.com.app.appgaspedidos.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import pe.com.app.appgaspedidos.repository.model.Estado;

import java.util.List;

public class EstadoRepository {

    @PersistenceContext(unitName = "perciPOSTGREL")
    private EntityManager entityManager;

    @Transactional
    public void guardar(Estado estado) {
        entityManager.persist(estado);
    }

    public Estado buscarPorId(Long id) {
        return entityManager.find(Estado.class, id);
    }

    public List<Estado> listarTodos() {
        return entityManager.createQuery("SELECT e FROM Estado e", Estado.class).getResultList();
    }

    @Transactional
    public void eliminar(Long id) {
        Estado estado = buscarPorId(id);
        if (estado != null) {
            entityManager.remove(estado);
        }
    }
}

