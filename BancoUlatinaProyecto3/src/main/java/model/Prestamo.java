package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Prestamo {

    private int idPrestamo;
    private int idCliente;
    private String tipo; // personal o hipotecario
    private BigDecimal monto;
    private int plazoMeses;
    private BigDecimal tasaInteres; // anual
    private Date fechaSolicitud;
    private String estado; // activo, pagado, mora

    public Prestamo() {}

    public Prestamo(int idPrestamo, int idCliente, String tipo, BigDecimal monto,
                    int plazoMeses, BigDecimal tasaInteres, Date fechaSolicitud, String estado) {
        this.idPrestamo = idPrestamo;
        this.idCliente = idCliente;
        this.tipo = tipo;
        this.monto = monto;
        this.plazoMeses = plazoMeses;
        this.tasaInteres = tasaInteres;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(int idPrestamo) { this.idPrestamo = idPrestamo; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public int getPlazoMeses() { return plazoMeses; }
    public void setPlazoMeses(int plazoMeses) { this.plazoMeses = plazoMeses; }

    public BigDecimal getTasaInteres() { return tasaInteres; }
    public void setTasaInteres(BigDecimal tasaInteres) { this.tasaInteres = tasaInteres; }

    public Date getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(Date fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
