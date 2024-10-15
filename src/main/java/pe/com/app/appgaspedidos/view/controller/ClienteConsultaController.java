package pe.com.app.appgaspedidos.view.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;
import pe.com.app.appgaspedidos.repository.model.Cliente;
import pe.com.app.appgaspedidos.security.Usuario;
import pe.com.app.appgaspedidos.service.ClienteService;
import pe.com.app.appgaspedidos.view.model.ClienteModel;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@Named
@ViewScoped
@Data
public class ClienteConsultaController  implements Serializable {
    private static final Logger logger = LogManager.getLogger(ClienteConsultaController.class);  // Se usa Log4j para registrar mensajes

    @Inject
    private ClienteService clienteService;
    @Inject
    private Usuario usuario;

    private ClienteModel clienteModel;

    @PostConstruct
    public void init() {
        clienteModel = new ClienteModel();
        clienteService.insertarClientesSiVacia();

        clienteModel.setClientes(clienteService.obtenerTodosLosClientes());

    }

    public void guardarCLiente(){
       try {
           if(clienteModel.getSeleccionadoCliente() != null){
               clienteService.actualizarCliente(clienteModel.getSeleccionadoCliente());
               clienteModel.setClientes(clienteService.obtenerTodosLosClientes());
               PrimeFaces.current().executeScript("PF('manageClienteDialog').hide()");
               FacesContext.getCurrentInstance().addMessage(null,
                       new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Actualizado", null));
           } else {
               FacesContext.getCurrentInstance().addMessage(null,
                       new FacesMessage(FacesMessage.SEVERITY_INFO, "Selecione un CLiente", null));
           }
       }catch (Exception e){
           FacesContext.getCurrentInstance().addMessage(null,
                   new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar Cliente", null));
       }




        PrimeFaces.current().ajax().update(  "formConsultaCliente");
    }
    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }

        Cliente cliente = (Cliente) value;

        // Filtrar por diferentes campos del cliente
        return cliente.getNombre().toLowerCase().contains(filterText)
                || cliente.getApellidos().toLowerCase().contains(filterText)
                || cliente.getDni().toLowerCase().contains(filterText)
                || (cliente.getDireccion() != null && cliente.getDireccion().toLowerCase().contains(filterText))
                || (cliente.getCorreo() != null && cliente.getCorreo().toLowerCase().contains(filterText))
                || cliente.getTelefono().contains(filterText);  // Filtrar por teléfono también
    }

}
