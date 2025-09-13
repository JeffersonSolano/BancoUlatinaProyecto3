package controller;

import model.Prestamo;
import model.Cuota;
import service.ServicePrestamo;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class PrestamoController implements Serializable {
    private static final long serialVersionUID = 1L;

    private Prestamo prestamoNuevo = new Prestamo();
    private List<Prestamo> prestamos;

    private ServicePrestamo servicePrestamo = new ServicePrestamo();

    // Getter y Setter
    public Prestamo getPrestamoNuevo() {
        return prestamoNuevo;
    }

    public void setPrestamoNuevo(Prestamo prestamoNuevo) {
        this.prestamoNuevo = prestamoNuevo;
    }

    public List<Prestamo> getPrestamos() {
        if (prestamos == null) {
            prestamos = servicePrestamo.listarPrestamos();
        }
        return prestamos;
    }

    // Agregar préstamo
    public String agregarPrestamo() {
        boolean exito = servicePrestamo.agregarPrestamo(prestamoNuevo);
        if (exito) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Éxito",
                    "Préstamo registrado correctamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            // Recargar lista
            prestamos = servicePrestamo.listarPrestamos();
            prestamoNuevo = new Prestamo(); // limpiar formulario
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "No se pudo registrar el préstamo.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null; // mantener en la misma página
    }

    // Listar cuotas de un préstamo
    public List<Cuota> listarCuotas(int idPrestamo) {
        return servicePrestamo.listarCuotas(idPrestamo);
    }
}
