package controller;

import model.Cuenta;
import service.ServiceCuenta;
import java.util.List;
import java.util.Date;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "cuentaController")
@SessionScoped
public class CuentaController implements Serializable {

    private static final long serialVersionUID = 1L;

    private Cuenta cuentaNueva = new Cuenta();
    private List<Cuenta> cuentas;
    private int cedulaCliente;

    public Cuenta getCuentaNueva() { return cuentaNueva; }
    public void setCuentaNueva(Cuenta cuentaNueva) { this.cuentaNueva = cuentaNueva; }

    public List<Cuenta> getCuentas() {
        if (cuentas == null) {
            ServiceCuenta service = new ServiceCuenta();
            cuentas = service.listarCuentas();
        }
        return cuentas;
    }

    public int getCedulaCliente() { return cedulaCliente; }
    public void setCedulaCliente(int cedulaCliente) { this.cedulaCliente = cedulaCliente; }

    public String agregarCuenta() {
        ServiceCuenta service = new ServiceCuenta();

        if (cuentaNueva.getEstado() != null) {
            cuentaNueva.setEstado(cuentaNueva.getEstado().toLowerCase());
        }
        if (cuentaNueva.getTipoCuenta() != null) {
            cuentaNueva.setTipoCuenta(cuentaNueva.getTipoCuenta().toLowerCase());
        }
        if (cuentaNueva.getMoneda() != null) {
            cuentaNueva.setMoneda(cuentaNueva.getMoneda().toUpperCase());
        }

        cuentaNueva.setIdCliente(cedulaCliente);

        // Insertar cuenta
        boolean exito = service.agregarCuenta(cuentaNueva);

        FacesMessage msg;
        if (exito) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Éxito",
                    "Cuenta agregada correctamente. Código: " + cuentaNueva.getCodigoCuenta());

            cuentas = service.listarCuentas();
            cuentaNueva = new Cuenta();
            cedulaCliente = 0;

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "No se pudo agregar la cuenta. Revise los datos ingresados.");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;
    }
}
