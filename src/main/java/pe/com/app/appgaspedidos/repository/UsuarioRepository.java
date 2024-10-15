package pe.com.app.appgaspedidos.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import pe.com.app.appgaspedidos.repository.model.Usuario;

import java.util.List;

@ApplicationScoped
public class UsuarioRepository {

    @PersistenceContext(unitName = "perciPOSTGREL")
    private EntityManager entityManager;

    @Transactional
    public void guardar(Usuario usuario) {
        entityManager.persist(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return entityManager.find(Usuario.class, id);
    }

    public List<Usuario> listarTodos() {
        return entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    @Transactional
    public void eliminar(Long id) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            entityManager.remove(usuario);
        }
    }

    public Usuario buscarPorUsernameYClave(String username, String clave) {
        try {
            return entityManager.createQuery("SELECT u FROM Usuario u WHERE u.username = :username AND u.clave = :clave", Usuario.class)
                    .setParameter("username", username)
                    .setParameter("clave", clave)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Si no se encuentra el usuario
        }
    }
}
