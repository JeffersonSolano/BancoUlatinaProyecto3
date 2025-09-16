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
    private Prestamo prestamoSeleccionado;
    private List<Prestamo> prestamos = new ArrayList<>();
    private List<Cuota> cuotas = new ArrayList<>();

    private ServicePrestamo servicePrestamo = new ServicePrestamo();

    // Getters y Setters
    public Prestamo getPrestamoNuevo() {
        return prestamoNuevo;
    }

    public void setPrestamoNuevo(Prestamo prestamoNuevo) {
        this.prestamoNuevo = prestamoNuevo;
    }

    public Prestamo getPrestamoSeleccionado() {
        return prestamoSeleccionado;
    }

    public void setPrestamoSeleccionado(Prestamo prestamoSeleccionado) {
        this.prestamoSeleccionado = prestamoSeleccionado;
    }

    public List<Prestamo> getPrestamos() {
        prestamos = servicePrestamo.listarPrestamos();
        return prestamos;
    }

    public List<Cuota> getCuotas() {
        // Solo actualizamos si hay un préstamo seleccionado
        if (prestamoSeleccionado != null) {
            // Evitamos llamar múltiples veces al servicio si la lista ya está cargada
            if (cuotas.isEmpty() || cuotas.get(0).getIdPrestamo() != prestamoSeleccionado.getIdPrestamo()) {
                cuotas = servicePrestamo.listarCuotas(prestamoSeleccionado.getIdPrestamo());
            }
        } else {
            cuotas = new ArrayList<>();
        }
        return cuotas;
    }

    public void setCuotas(List<Cuota> cuotas) {
        this.cuotas = cuotas;
    }

    // Agregar préstamo
    public String agregarPrestamo() {
        prestamoNuevo.setFechaSolicitud(new java.util.Date());
        prestamoNuevo.setEstado("activo");

        boolean exito = servicePrestamo.agregarPrestamo(prestamoNuevo);
        if (exito) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Éxito", "Préstamo registrado correctamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            prestamoNuevo = new Prestamo();
            prestamos = servicePrestamo.listarPrestamos();
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "No se pudo registrar el préstamo.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return null;
    }

    // Listener para seleccionar un préstamo y cargar sus cuotas
    public void seleccionarPrestamo() {
        if (prestamoSeleccionado != null) {
            cuotas = servicePrestamo.listarCuotas(prestamoSeleccionado.getIdPrestamo());
        } else {
            cuotas = new ArrayList<>();
        }
    }

    public void verCuotas(Prestamo p) {
        if (p != null) {
            this.prestamoSeleccionado = p;
            this.cuotas = servicePrestamo.listarCuotas(p.getIdPrestamo());
        } else {
            this.cuotas = new ArrayList<>();
        }
    }

}
