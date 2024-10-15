package pe.com.app.appgaspedidos.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pe.com.app.appgaspedidos.repository.EstadoRepository;
import pe.com.app.appgaspedidos.repository.model.Estado;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class EstadoService {

    @Inject
    private EstadoRepository estadoRepository;

    @Transactional
    public void guardarEstado(Estado estado) {
        // Asigna la fecha actual del sistema
        estado.setFechaRegistro(LocalDateTime.now());
        estadoRepository.guardar(estado);
    }

    public Estado buscarPorId(Long id) {
        return estadoRepository.buscarPorId(id);
    }

    public List<Estado> listarTodos() {
        return estadoRepository.listarTodos();
    }

    @Transactional
    public void eliminarEstado(Long id) {
        estadoRepository.eliminar(id);
    }
}
