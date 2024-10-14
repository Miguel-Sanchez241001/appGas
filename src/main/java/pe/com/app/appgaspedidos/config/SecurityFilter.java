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
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestedPath = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Excluir páginas públicas y recursos estáticos (resources y jakarta.faces.resource)
        if (requestedPath.startsWith("/resources/") || requestedPath.contains("jakarta.faces.resource")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Si la ruta es /index.xhtml
        if (requestedPath.equals("/index.xhtml") || requestedPath.equals("/")  ) {
            // Verificar si el usuario ya está autenticado
            if (usuario != null && usuario.getUsername() != null) {
                // Redirigir a principal.xhtml si ya está autenticado
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/principal.xhtml");
                return;
            }
            // Si no está autenticado, permitir el acceso a index.xhtml
            filterChain.doFilter(request, response);
            return;
        }

        // Para cualquier otra ruta que contenga .xhtml
        if (requestedPath.endsWith(".xhtml")) {
            // Verificar si el usuario está autenticado
            if (usuario == null || usuario.getUsername() == null) {
                logger.warn("[SecurityFilter] - Usuario no autenticado, redirigiendo al login");
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.xhtml");
                return;
            }
        }

        // Continuar con el filtro si todo es correcto
        filterChain.doFilter(request, response);
    }


    @Override
    public void destroy() {
        logger.info("[SecurityFilter] - Destruyendo filtro de seguridad");
    }
}
