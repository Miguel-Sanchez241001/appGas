package pe.com.app.appgaspedidos.view.controller;

import jakarta.annotation.PostConstruct;
import jakarta.el.MethodExpression;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.ActionListener;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.com.app.appgaspedidos.security.Usuario;
import org.omnifaces.util.Faces;
import java.io.IOException;
import java.io.Serializable;

 

@Named
@SessionScoped
@Data
public class SeguridadController implements Serializable {
    private static final Logger logger = LogManager.getLogger(SeguridadController.class);
    @PostConstruct
    public void init() {

    }
    @Inject
    private Usuario usuario;

    // Método para obtener el nombre de usuario
    public String getUsername() {
        return usuario != null ? usuario.getUsername() : null;
    }

    // Método para verificar roles
    public boolean hasRole(String role) {
        return usuario != null && usuario.getRoles().contains(role);
    }

    public int tiempoMaximoInactividad() {
        return Integer
                .parseInt(  "10") * 1000 * 60;
    }

    public void forzarCierreSesion() {
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
     }

    public String mensajeSesionExpirada() {
        return "Sesion expirada. Por favor, inicia sesion de nuevo.";
    }

    public String paginaLogin() {
         return "/index.xhtml?faces-redirect=true";
     }

    public boolean estaSeleccionado(String pagina) {
     	FacesContext fctx = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fctx.getExternalContext()
                .getRequest();
        StringBuffer url = req.getRequestURL();
        return url.toString().contains(pagina);
    }

    public boolean renderizar(String args) {
        if (hasRole("ADMIN")){
            return true;
        }
            return hasRole(args);


    }

    public void redireccionar(String pagina) {
        Faces.redirect(pagina);
    }

}
