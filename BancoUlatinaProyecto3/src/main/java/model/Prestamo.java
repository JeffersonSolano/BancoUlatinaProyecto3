package model;

import java.util.Date;

public class Prestamo {

    private int idPrestamo;       // PK interna
    private int idCliente;        // FK hacia clientes.cedula
    private String tipo;          // personal / hipotecario
    private Double monto;
    private int plazoMeses;
    private Double tasaInteres;
    private Date fechaSolicitud;
    private String estado;        // activo / pagado / mora

    public Prestamo() {
    }

    public Prestamo(int idPrestamo, int idCliente, String tipo, Double monto, int plazoMeses, Double tasaInteres, Date fechaSolicitud, String estado) {
        this.idPrestamo = idPrestamo;
        this.idCliente = idCliente;
        this.tipo = tipo;
        this.monto = monto;
        this.plazoMeses = plazoMeses;
        this.tasaInteres = tasaInteres;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public Double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(Double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
