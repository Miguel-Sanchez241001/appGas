package pe.com.app.appgaspedidos.view.model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
@Named
public class LoginModel implements Serializable {
    private String nombreEmpresa = "GESA PERU GAS E.I.R.L";
    private String nombreSistema = "Sistema de Administración de Solcitudes de GESA";
    private String abreviaturaSistema = "SASA";

}
