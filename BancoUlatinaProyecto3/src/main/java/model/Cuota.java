package model;

import java.util.Date;

public class Cuota {

    private int idCuota;
    private int idPrestamo;
    private int nroCuota;
    private Date fechaVencimiento;
    private double montoCuota;
    private String estado; // pendiente, pagada, mora

    public Cuota() {
    }

    public Cuota(int idCuota, int idPrestamo, int nroCuota, Date fechaVencimiento, double montoCuota, String estado) {
        this.idCuota = idCuota;
        this.idPrestamo = idPrestamo;
        this.nroCuota = nroCuota;
        this.fechaVencimiento = fechaVencimiento;
        this.montoCuota = montoCuota;
        this.estado = estado;
    }

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

    public double getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(double montoCuota) {
        this.montoCuota = montoCuota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
}
