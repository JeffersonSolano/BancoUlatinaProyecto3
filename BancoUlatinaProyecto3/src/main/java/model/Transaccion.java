package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Transaccion {

    private int idTransaccion;
    private int idCuentaOrigen;
    private int idCuentaDestino;
    private String tipo;
    private BigDecimal monto;
    private Date fecha;
    private String estado;
    private boolean autorizacion;
    private String descripcion;

    // Constructor vacío (necesario para JSF)
    public Transaccion() {
    }

    // Constructor completo
    public Transaccion(int idTransaccion, int idCuentaOrigen, int idCuentaDestino,
                       String tipo, BigDecimal monto, Date fecha,
                       String estado, boolean autorizacion, String descripcion) {
        this.idTransaccion = idTransaccion;
        this.idCuentaOrigen = idCuentaOrigen;
        this.idCuentaDestino = idCuentaDestino;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
        this.estado = estado;
        this.autorizacion = autorizacion;
        this.descripcion = descripcion;
    }

    // Constructor sin idTransaccion (para inserciones)
    public Transaccion(int idCuentaOrigen, int idCuentaDestino,
                       String tipo, BigDecimal monto, Date fecha,
                       String estado, boolean autorizacion, String descripcion) {
        this.idCuentaOrigen = idCuentaOrigen;
        this.idCuentaDestino = idCuentaDestino;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
        this.estado = estado;
        this.autorizacion = autorizacion;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public int getIdCuentaOrigen() {
        return idCuentaOrigen;
    }

    public void setIdCuentaOrigen(int idCuentaOrigen) {
        this.idCuentaOrigen = idCuentaOrigen;
    }

    public int getIdCuentaDestino() {
        return idCuentaDestino;
    }

    public void setIdCuentaDestino(int idCuentaDestino) {
        this.idCuentaDestino = idCuentaDestino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(boolean autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
