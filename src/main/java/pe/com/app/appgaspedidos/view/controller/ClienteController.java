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
import org.primefaces.event.FlowEvent;
import pe.com.app.appgaspedidos.repository.model.Cliente;
import pe.com.app.appgaspedidos.security.Usuario;
import pe.com.app.appgaspedidos.service.ClienteService;
import pe.com.app.appgaspedidos.view.model.ClienteModel;

import java.io.Serializable;

@Named
@ViewScoped
@Data
public class ClienteController implements Serializable {
    private static final Logger logger = LogManager.getLogger(LoginController.class);  // Se usa Log4j para registrar mensajes

    @Inject
    private ClienteService clienteService;
    @Inject
    private Usuario usuario;
    private boolean skip;
    private ClienteModel clienteModel;

    @PostConstruct
    public void init() {
        clienteModel = new ClienteModel();
        clienteModel.setExisteDni(false);

    }


    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false; //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public void verificarDni() {
        String dni = clienteModel.getDni();

        // Lógica para verificar si el DNI ya está registrado
        clienteModel.setExisteDni(clienteService.existeDni(dni)); // Método del servicio que verifica la existencia del DNI

        if (clienteModel.isExisteDni()) {
            // Mostrar mensaje si el DNI ya está registrado
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "El DNI ya está registrado", null));

            // Otras acciones que desees realizar cuando el DNI ya exista
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "DNI disponible", null));
        }
        PrimeFaces.current().ajax().update( "nextBtnCliente");
     }


    public void registrarCliente() {
        try {
            // Crear entidad Cliente a partir del modelo
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setNombre(clienteModel.getNombre());
            nuevoCliente.setApellidos(clienteModel.getApellidos());
            nuevoCliente.setDni(clienteModel.getDni());
            nuevoCliente.setDireccion(clienteModel.getDireccion());
            nuevoCliente.setTelefono(clienteModel.getTelefono());
            nuevoCliente.setCorreo(clienteModel.getCorreo());
            // Llamar al servicio para persistir el cliente
            clienteService.crearCliente(nuevoCliente);

            // Limpiar el modelo después de guardar
            clienteModel = new ClienteModel();

            logger.info("Cliente registrado exitosamente.");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente registrado exitosamente", null));

            // Mover el wizard al primer tab sin actualizar la vista completa
            PrimeFaces.current().executeScript("setTimeout(function() { PF('wizardWidget').loadStep('personal', true); }, 2000);");

        } catch (Exception e) {
            logger.error("Error al registrar el cliente", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al registrar el cliente", e.getMessage()));
        }
    }
}
