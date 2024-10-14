package pe.com.app.appgaspedidos.security;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;
@SessionScoped
@Data
@NoArgsConstructor
@AllArgsConstructor
@Named("usuario") // Esto hace que el bean esté disponible como #{usuario} en EL
public class Usuario implements Serializable {
    private String username;
    private Set<String> roles;



    public boolean hasRole(String role) {
        return roles.contains(role);
    }
}
