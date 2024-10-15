package pe.com.app.appgaspedidos.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pe.com.app.appgaspedidos.repository.SolicitudRepository;
import pe.com.app.appgaspedidos.repository.model.Estado;
import pe.com.app.appgaspedidos.repository.model.Solicitud;
import pe.com.app.appgaspedidos.tranversal.enums.TipoEstado;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class SolicitudService {

    @Inject
    private SolicitudRepository solicitudRepository;
    @Inject
    private EstadoService estadoService;
    @Transactional
     public Long guardarSolicitud(Solicitud solicitud) {
        return solicitudRepository.guardar(solicitud);
    }


    public Solicitud buscarPorId(Long id) {
        return solicitudRepository.buscarPorId(id);
    }

    public List<Solicitud> listarTodasLasSolicitudes() {
        return solicitudRepository.listarTodos();
    }

    @Transactional
    public void eliminarSolicitud(Long id) {
        solicitudRepository.eliminar(id);
    }

    public List<Solicitud> buscarPorEstado(TipoEstado tipoEstado) {
        return solicitudRepository.buscarPorEstado(tipoEstado);
    }

    public List<Solicitud> buscarPorDniCliente(String dni) {
        return solicitudRepository.buscarPorDniCliente(dni);
    }
}
