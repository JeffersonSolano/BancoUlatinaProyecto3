package model;

import java.util.Date;

public class Transaccion {

    private int idTransaccion;       // PK autoincremental
    private int idCuentaOrigen;      // FK: id_cuentas_clientes de origen
    private int idCuentaDestino;     // FK: id_cuentas_clientes de destino (0 si no aplica)
    private String codigoCuentaOrigen;  // Para recibir de la vista
    private String codigoCuentaDestino; // Para recibir de la vista
    private String tipo;             // deposito / retiro / transferencia
    private Double monto;
    private Date fecha;
    private String estado;
    private boolean autorizacion;
    private String descripcion;

    public Transaccion() {
    }

    public Transaccion(int idTransaccion, int idCuentaOrigen, int idCuentaDestino, String codigoCuentaOrigen, String codigoCuentaDestino, String tipo, Double monto, Date fecha, String estado, boolean autorizacion, String descripcion) {
        this.idTransaccion = idTransaccion;
        this.idCuentaOrigen = idCuentaOrigen;
        this.idCuentaDestino = idCuentaDestino;
        this.codigoCuentaOrigen = codigoCuentaOrigen;
        this.codigoCuentaDestino = codigoCuentaDestino;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
        this.estado = estado;
        this.autorizacion = autorizacion;
        this.descripcion = descripcion;
    }

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

    public String getCodigoCuentaOrigen() {
        return codigoCuentaOrigen;
    }

    public void setCodigoCuentaOrigen(String codigoCuentaOrigen) {
        this.codigoCuentaOrigen = codigoCuentaOrigen;
    }

    public String getCodigoCuentaDestino() {
        return codigoCuentaDestino;
    }

    public void setCodigoCuentaDestino(String codigoCuentaDestino) {
        this.codigoCuentaDestino = codigoCuentaDestino;
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
