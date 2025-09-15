package controller;

import model.Cuenta;
import service.ServiceCuenta;
import java.util.List;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "cuentaController") // Aseguramos el nombre correcto
@SessionScoped
public class CuentaController implements Serializable {

    private static final long serialVersionUID = 1L;

    private Cuenta cuentaNueva = new Cuenta(); // Objeto para el formulario
    private List<Cuenta> cuentas;              // Lista de cuentas existentes
    private int cedulaCliente;                 // Para seleccionar cliente al crear la cuenta

    // Getters y setters
    public Cuenta getCuentaNueva() {
        return cuentaNueva;
    }

    public void setCuentaNueva(Cuenta cuentaNueva) {
        this.cuentaNueva = cuentaNueva;
    }

    public List<Cuenta> getCuentas() {
        if (cuentas == null) {
            ServiceCuenta service = new ServiceCuenta();
            cuentas = service.listarCuentas();
        }
        return cuentas;
    }

    public int getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(int cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    // Método para agregar una nueva cuenta
    public String agregarCuenta() {
        ServiceCuenta service = new ServiceCuenta();

        // Asegurarse que todos los campos tipo ENUM estén en minúscula
        if (cuentaNueva.getEstado() != null) {
            cuentaNueva.setEstado(cuentaNueva.getEstado().toLowerCase());
        }
        if (cuentaNueva.getTipoCuenta() != null) {
            cuentaNueva.setTipoCuenta(cuentaNueva.getTipoCuenta().toLowerCase());
        }
        if (cuentaNueva.getMoneda() != null) {
            cuentaNueva.setMoneda(cuentaNueva.getMoneda().toUpperCase()); // opcional: moneda siempre en mayúscula
        }

        // Asignar cédula y generar código de cuenta
        cuentaNueva.setIdCliente(cedulaCliente);
        cuentaNueva.setCodigoCuenta(service.generarCodigoCuenta());

        // Insertar la cuenta en la base de datos
        boolean exito = service.agregarCuenta(cuentaNueva);

        // Mensaje según resultado
        FacesMessage msg;
        if (exito) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Éxito",
                    "Cuenta agregada correctamente. Código: " + cuentaNueva.getCodigoCuenta());

            // Recargar lista y limpiar formulario
            cuentas = service.listarCuentas();
            cuentaNueva = new Cuenta();
            cedulaCliente = 0;

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "No se pudo agregar la cuenta. Revise los datos ingresados.");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null; // permanecer en la misma página
    }
}
