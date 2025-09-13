package controller;

import model.Transaccion;
import service.ServiceTransaccion;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class TransaccionController implements Serializable {
    private static final long serialVersionUID = 1L;

    private Transaccion transaccionNueva = new Transaccion(); // Para el formulario
    private List<Transaccion> transacciones;                  // Historial de transacciones

    // Getter y Setter
    public Transaccion getTransaccionNueva() {
        return transaccionNueva;
    }

    public void setTransaccionNueva(Transaccion transaccionNueva) {
        this.transaccionNueva = transaccionNueva;
    }

    public List<Transaccion> getTransacciones() {
        if (transacciones == null) {
            ServiceTransaccion service = new ServiceTransaccion();
            transacciones = service.listarTransacciones();
        }
        return transacciones;
    }

    // Método para agregar transacción
    public String agregarTransaccion() {
        ServiceTransaccion service = new ServiceTransaccion();

        boolean exito = service.agregarTransaccion(transaccionNueva);

        if (exito) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Éxito",
                    "Transacción realizada correctamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "No se pudo realizar la transacción. Saldo insuficiente o datos incorrectos.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        // Recargar lista de transacciones para reflejar cambios
        transacciones = service.listarTransacciones();

        // Limpiar formulario
        transaccionNueva = new Transaccion();

        return null; // Mantenerse en la misma página
    }
}
