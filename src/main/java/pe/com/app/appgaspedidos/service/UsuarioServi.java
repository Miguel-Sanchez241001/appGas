package pe.com.app.appgaspedidos.service;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import pe.com.app.appgaspedidos.repository.UserRepo;
import pe.com.app.appgaspedidos.repository.model.Usuario;

@Named
@ApplicationScoped
public class UsuarioServi {

    @Inject
    private UserRepo miRepositorio;

    public void realizarOperacion() {
         miRepositorio.guardar(new Usuario());
    }
}
