package pe.com.app.appgaspedidos.view.controller;

import jakarta.annotation.PostConstruct;
import jakarta.el.MethodExpression;
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
import org.primefaces.util.LangUtils;
import pe.com.app.appgaspedidos.repository.model.Cliente;
import pe.com.app.appgaspedidos.repository.model.Estado;
import pe.com.app.appgaspedidos.repository.model.Solicitud;
import pe.com.app.appgaspedidos.security.Usuario;
import pe.com.app.appgaspedidos.service.ClienteService;
import pe.com.app.appgaspedidos.service.EstadoService;
import pe.com.app.appgaspedidos.service.SolicitudService;
import pe.com.app.appgaspedidos.tranversal.enums.TipoEstado;
import pe.com.app.appgaspedidos.tranversal.enums.TipoSolicitud;
import pe.com.app.appgaspedidos.view.model.SolicitudModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Named
@ViewScoped
@Data
public class SolicitudConsultaController implements Serializable {
    private static final Logger logger = LogManager.getLogger(SolicitudConsultaController.class);  // Se usa Log4j para registrar mensajes
    @Inject
    private SolicitudService solicitudService;
    @Inject
    private Usuario usuario;
    @Inject
    private ClienteService clienteService;
    @Inject
    private EstadoService estadoService;
    private SolicitudModel solicitudModel;

    @PostConstruct
    public void init() {
        iniciarView();
    }

    public void buscarClientePorDni() {
        try {
            Cliente cliente = clienteService.buscarPorDni(solicitudModel.getDni());
            if (cliente != null) {
                solicitudModel.setNombreCompleto(cliente.getNombre() + " " + cliente.getApellidos());
                solicitudModel.setCliente(cliente);
            } else {
                solicitudModel.setNombreCompleto(null);
                solicitudModel.setDni(null);
            }

            PrimeFaces.current().ajax().update("panelCliente");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay Usuario registrado con el Numero de DNI", null));
        }
    }

    public void registrarSolicitud() {
        try {
            Solicitud nuevaSolicitud = new Solicitud();
            nuevaSolicitud.setCliente(solicitudModel.getCliente());
            nuevaSolicitud.setTipo(solicitudModel.getSolicitudUpdatenew().getTipo());
            nuevaSolicitud.setDireccion(solicitudModel.getDireccionSolicitada());
            nuevaSolicitud.setObservaciones(solicitudModel.getObservaciones());
            nuevaSolicitud.setUsuarioRegistro(usuario.getUsername());
            nuevaSolicitud.setFechaRegistro(LocalDateTime.now()); // La fecha de registro es la actual
            solicitudService.guardarSolicitud(nuevaSolicitud);

            Estado estadoInicial = new Estado(TipoEstado.REGISTRADA, nuevaSolicitud);
            estadoService.guardarEstado(estadoInicial); // Asegúrate de tener un servicio para guardar el estado
            logger.info("Soliciutd registrado exitosamente.");
            iniciarView();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Soliciutd registrado exitosamente", null));
            // Mover el wizard al primer tab sin actualizar la vista completa
            PrimeFaces.current().executeScript("setTimeout(function() { PF('wizardSolicutdWidget').loadStep('cliente', true); }, 2000);");


        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al registrar Soliciutd", null));
        }
    }

    public void cambiarEstado(Solicitud solicitud, String nuevoEstado) {
        try {
            // Cambiar el estado de la solicitud
            TipoEstado estadoEnum = TipoEstado.valueOf(nuevoEstado);

            // Crear un nuevo estado para la solicitud
            Estado nuevoEstadoObj = new Estado(estadoEnum, solicitud);

            // Guardar el nuevo estado en la base de datos
            estadoService.guardarEstado(nuevoEstadoObj);

            // Actualizar la lista de solicitudes
            iniciarView();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El estado de la solicitud ha sido actualizado a " + nuevoEstado));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo cambiar el estado de la solicitud"));
        }
        PrimeFaces.current().ajax().update("formConsultaSolicitud");
    }


    private boolean skip;

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false; //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public void iniciarView() {
        Solicitud solisave = new Solicitud(); // Crear una nueva solicitud new
        solisave.setTipo(TipoSolicitud.GAS);
        solicitudModel = new SolicitudModel();
        solicitudModel.setSolicitudes(solicitudService.listarTodasLasSolicitudes());
        solicitudModel.setListaTipoSolicitudes(List.of(TipoSolicitud.values()));
        solicitudModel.setSolicitudUpdatenew(solisave);
    }




    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }

        Solicitud solicitud = (Solicitud) value;

        // Filtrar por diferentes campos de la solicitud
        return solicitud.getFechaRegistroFormatted().toLowerCase().contains(filterText)
                || solicitud.getTipo().name().toLowerCase().contains(filterText)
                || solicitud.getCliente().getNombre().toLowerCase().contains(filterText)
                || solicitud.getCliente().getDni().toLowerCase().contains(filterText)
                || (solicitud.getCliente().getDireccion() != null && solicitud.getCliente().getDireccion().toLowerCase().contains(filterText))
                || (solicitud.getCliente().getCorreo() != null && solicitud.getCliente().getCorreo().toLowerCase().contains(filterText))
                || solicitud.getCliente().getTelefono().contains(filterText)
                || solicitud.getEstadoReciente().getEstado().name().toLowerCase().contains(filterText);
    }

}
