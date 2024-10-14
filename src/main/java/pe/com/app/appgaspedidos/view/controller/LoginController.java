package pe.com.app.appgaspedidos.view.controller;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.com.app.appgaspedidos.security.Usuario;
import pe.com.app.appgaspedidos.view.model.LoginModel;

import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
@Data
public class LoginController  implements Serializable {
    private static final Logger logger = LogManager.getLogger(LoginController.class);  // Se usa Log4j para registrar mensajes
    @Inject
    private LoginModel loginModel;
    @Inject
    private Usuario usuario; // Inyectamos el bean Usuario

    private String username;
    private String password;
    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Inject
    private SecurityContext securityContext;
    @PostConstruct
    public void init() {

    }


    // Lógica de inicio de sesión
    public String login() {
        logger.info("[loginController] - Iniciando método iniciarSesion");




        FacesContext context = FacesContext.getCurrentInstance();
        CredentialValidationResult result = identityStoreHandler.validate(
                new UsernamePasswordCredential(username, password));

        if (result.getStatus() == CredentialValidationResult.Status.VALID) {
            usuario.setUsername(result.getCallerPrincipal().getName());
            usuario.setRoles(result.getCallerGroups());

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inicio de sesión correcto", "Bienvenido, " + username + "!"));
            logger.info("[loginController] - Login successful");
            return "principal.xhtml?faces-redirect=true";
        } else {
            // Login failed
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login fallido", "Nombre de usuario o contraseña incorrectos"));
            logger.info("[loginController] - Login failed");
            return null;
        }

    }

    public String logout() {
        logger.info("[loginController] - Iniciando método logout");

        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession(); // Invalidar la sesión actual
        usuario = null; // Opcional, si quieres asegurarte de que el bean se reinicia

        logger.info("[loginController] - Sesión invalidada");

        // Redireccionar a la página de inicio
        return "index.xhtml?faces-redirect=true";
    }





}
