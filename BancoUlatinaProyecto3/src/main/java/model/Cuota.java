package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Cuota {

    private int idCuota;
    private int idPrestamo;
    private int nroCuota;
    private Date fechaVencimiento;
    private BigDecimal montoCuota;
    private String estado; // pendiente, pagada, mora

    public Cuota() {
    }

    public Cuota(int idCuota, int idPrestamo, int nroCuota, BigDecimal montoCuota, Date fechaVencimiento, String estado) {
        this.idCuota = idCuota;
        this.idPrestamo = idPrestamo;
        this.nroCuota = nroCuota;
        this.montoCuota = montoCuota;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdCuota() {
        return idCuota;
    }

    public void setIdCuota(int idCuota) {
        this.idCuota = idCuota;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getNroCuota() {
        return nroCuota;
    }

    public void setNroCuota(int nroCuota) {
        this.nroCuota = nroCuota;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(BigDecimal montoCuota) {
        this.montoCuota = montoCuota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
