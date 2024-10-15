package pe.com.app.appgaspedidos.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pe.com.app.appgaspedidos.repository.UsuarioRepository;
import pe.com.app.appgaspedidos.repository.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class UsuarioService {

    @Inject
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void guardarUsuario(Usuario usuario) {
        // Asigna la fecha actual del sistema
        usuario.setFechaRegistro(LocalDateTime.now());
        usuarioRepository.guardar(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.buscarPorId(id);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    @Transactional
    public void eliminarUsuario(Long id) {
        usuarioRepository.eliminar(id);
    }

    // Método para validar el login
    public Usuario login(String username, String clave) {
        return usuarioRepository.buscarPorUsernameYClave(username, clave);
    }
}
