package pe.com.app.appgaspedidos.view.tranversal;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

public class ViewUtils {
    // Método para mostrar mensajes
    public static void mostrarMensaje(String resumen, String detalle, FacesMessage.Severity severidad) {
        FacesMessage mensaje = new FacesMessage(severidad, resumen, detalle);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
    }

    // Método para actualizar componentes
    public static void actualizarComponente(String componentId) {
        PrimeFaces.current().ajax().update(componentId);
    }
}
