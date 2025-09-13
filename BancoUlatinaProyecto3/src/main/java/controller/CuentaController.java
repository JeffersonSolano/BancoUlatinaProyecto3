package controller;

import model.Cuenta;
import service.ServiceCuenta;
import java.util.List;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
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

        // Generar código de cuenta automáticamente
        String codigo = service.generarCodigoCuenta(cedulaCliente);
        cuentaNueva.setCodigoCuenta(codigo);
        cuentaNueva.setIdCliente(cedulaCliente);

        // Insertar la cuenta en la base de datos
        boolean exito = service.agregarCuenta(cuentaNueva);
        if (!exito) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "No se pudo agregar la cuenta.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null; // permanecer en la misma página
        }

        // Recargar la lista de cuentas
        cuentas = service.listarCuentas();

        // Limpiar formulario
        cuentaNueva = new Cuenta();
        cedulaCliente = 0;

        // Mensaje de éxito
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Éxito",
                "Cuenta agregada correctamente. Código: " + codigo);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        return null; // permanecer en la misma página
    }
}
