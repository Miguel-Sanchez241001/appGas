// InMemoryIdentityStore.java
package pe.com.app.appgaspedidos.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@ApplicationScoped
public class InMemoryIdentityStore implements IdentityStore {

    private static final Logger logger = LogManager.getLogger(InMemoryIdentityStore.class);

    private Map<String, String> users = new HashMap<>();
    private Map<String, Set<String>> roles = new HashMap<>();

    public InMemoryIdentityStore() {
        logger.info("[InMemoryIdentityStore] - Constructor llamado");
        // Agrega usuarios y roles
        users.put("admin", "admin123");
        roles.put("admin", Set.of("ADMIN"));
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential upc = (UsernamePasswordCredential) credential;
            logger.info("[InMemoryIdentityStore] - Validando credenciales para el usuario: {}", upc.getCaller());

            String password = users.get(upc.getCaller());
            if (password != null && password.equals(upc.getPasswordAsString())) {
                logger.info("[InMemoryIdentityStore] - Credenciales válidas para el usuario: {}", upc.getCaller());
                return new CredentialValidationResult(upc.getCaller(), roles.get(upc.getCaller()));
            }

            logger.warn("[InMemoryIdentityStore] - Credenciales inválidas para el usuario: {}", upc.getCaller());
            return CredentialValidationResult.INVALID_RESULT;
        } else {
            logger.warn("[InMemoryIdentityStore] - Tipo de credencial no soportado: {}", credential.getClass().getName());
            return CredentialValidationResult.NOT_VALIDATED_RESULT;
        }
    }
}
