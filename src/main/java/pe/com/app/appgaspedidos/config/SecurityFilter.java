package pe.com.app.appgaspedidos.config;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.com.app.appgaspedidos.security.Usuario;

@WebFilter("/*")
public class SecurityFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(SecurityFilter.class);

    @Inject
    private Usuario usuario;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("[SecurityFilter] - Inicializando filtro de seguridad");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestedPath = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());


        // Excluir páginas públicas y recursos estáticos
        if (requestedPath.equals("/index.xhtml") || requestedPath.startsWith("/resources/") || requestedPath.contains("javax.faces.resource")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (usuario.getUsername() == null) {
            logger.warn("[SecurityFilter] - Usuario no autenticado, redirigiendo al login");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.xhtml");
            return;
        }

        //logger.info("[SecurityFilter] - Usuario autenticado: {}", usuario.getUsername());

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("[SecurityFilter] - Destruyendo filtro de seguridad");
    }
}
