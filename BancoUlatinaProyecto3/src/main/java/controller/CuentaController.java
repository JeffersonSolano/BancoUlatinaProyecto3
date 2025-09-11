package controller;

import model.Cuenta;
import service.ServiceCuenta;
import java.util.List;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class CuentaController implements Serializable {

    private static final long serialVersionUID = 1L;

    private Cuenta cuentaNueva = new Cuenta();
    private List<Cuenta> cuentasCliente;

    public Cuenta getCuentaNueva() {
        return cuentaNueva;
    }

    public void setCuentaNueva(Cuenta cuentaNueva) {
        this.cuentaNueva = cuentaNueva;
    }

    public List<Cuenta> getCuentasCliente() {
        return cuentasCliente;
    }

    // Crear cuenta
    public String crearCuenta() {
        ServiceCuenta servicioCuenta = new ServiceCuenta(); // ✅ instancia local

        if (servicioCuenta.crearCuenta(cuentaNueva)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Cuenta creada correctamente"));
            cuentaNueva = new Cuenta(); // limpiar formulario
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear la cuenta"));
        }
        return null; // mantener en la misma página
    }

    // Cargar las cuentas de un cliente específico
    public void cargarCuentas(int cedulaCliente) {
        ServiceCuenta servicioCuenta = new ServiceCuenta(); // ✅ instancia local
        cuentasCliente = servicioCuenta.listarCuentasPorCliente(cedulaCliente);
    }

    // Convertir tipo de cuenta a texto
    public String getTipoCuentaTexto(int tipo) {
        switch (tipo) {
            case 1:
                return "Ahorro";
            case 2:
                return "Corriente";
            default:
                return "Desconocido";
        }
    }

    // Convertir tipo de moneda a texto
    public String getMonedaTexto(int moneda) {
        switch (moneda) {
            case 1:
                return "CRC";
            case 2:
                return "USD";
            default:
                return "N/A";
        }
    }

    // Convertir estado de la cuenta a texto
    public String getEstadoTexto(int estado) {
        switch (estado) {
            case 1:
                return "Activa";
            case 2:
                return "Bloqueada";
            case 3:
                return "Cerrada";
            default:
                return "N/A";
        }
    }
}
