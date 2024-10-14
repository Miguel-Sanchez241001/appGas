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
import pe.com.app.appgaspedidos.security.Usuario;
import pe.com.app.appgaspedidos.service.ClienteService;
import pe.com.app.appgaspedidos.view.model.ClienteModel;

import java.io.Serializable;

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

}
