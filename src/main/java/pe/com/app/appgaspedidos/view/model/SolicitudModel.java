package pe.com.app.appgaspedidos.view.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.com.app.appgaspedidos.repository.model.Cliente;
import pe.com.app.appgaspedidos.repository.model.Solicitud;
import pe.com.app.appgaspedidos.tranversal.enums.TipoSolicitud;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudModel {

    private String nombreCompleto;
    private String dni;
    private String observaciones;
    private String direccionSolicitada;
    private Cliente cliente;
    private LocalDateTime fechaRegistro;
    private List<TipoSolicitud> listaTipoSolicitudes; ;
    private  Solicitud  seleccionadoSolicitud ;
    private  Solicitud  solicitudUpdatenew ;
    private List<Solicitud> solicitudes;
    private List<Solicitud> solicitudesFiltradas;
}
