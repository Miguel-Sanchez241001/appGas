package pe.com.app.appgaspedidos.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pe.com.app.appgaspedidos.repository.model.Usuario;

@ApplicationScoped
public class UserRepo {
    @PersistenceContext(unitName = "perciPOSTGREL")
    private EntityManager em;

    public void guardar(Usuario entidad) {
        em.persist(entidad);
    }

    public Usuario obtener(Long id) {
        return em.find(Usuario.class, id);
    }
}
