package controller;

import model.Prestamo;
import model.Cuota;
import service.ServicePrestamo;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class PrestamoController implements Serializable {
    private static final long serialVersionUID = 1L;

    private Prestamo prestamoNuevo = new Prestamo();
    private Prestamo prestamoSeleccionado; // Para la tabla
    private List<Prestamo> prestamos = new ArrayList<>();

    private ServicePrestamo servicePrestamo = new ServicePrestamo();

    // Getters y Setters
    public Prestamo getPrestamoNuevo() { return prestamoNuevo; }
    public void setPrestamoNuevo(Prestamo prestamoNuevo) { this.prestamoNuevo = prestamoNuevo; }

    public Prestamo getPrestamoSeleccionado() { return prestamoSeleccionado; }
    public void setPrestamoSeleccionado(Prestamo prestamoSeleccionado) { this.prestamoSeleccionado = prestamoSeleccionado; }

    public List<Prestamo> getPrestamos() {
        prestamos = servicePrestamo.listarPrestamos();
        return prestamos;
    }

    // Agregar préstamo
    public String agregarPrestamo() {
        if (prestamoNuevo == null) {
            prestamoNuevo = new Prestamo();
        }

        // Inicializamos fecha y estado
        prestamoNuevo.setFechaSolicitud(new java.util.Date());
        prestamoNuevo.setEstado("activo"); // o pendiente según tu lógica

        boolean exito = servicePrestamo.agregarPrestamo(prestamoNuevo);
        if (exito) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Éxito", "Préstamo registrado correctamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            prestamoNuevo = new Prestamo(); // limpiar formulario
            prestamos = servicePrestamo.listarPrestamos(); // recargar lista
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "No se pudo registrar el préstamo.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return null; // mantener en la misma página
    }

    // Listar cuotas de un préstamo (se usará después)
    public List<Cuota> listarCuotas() {
        if (prestamoSeleccionado != null) {
            return servicePrestamo.listarCuotas(prestamoSeleccionado.getIdPrestamo());
        }
        return new ArrayList<>();
    }
}
