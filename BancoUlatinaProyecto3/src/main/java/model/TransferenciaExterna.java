package model;

import java.math.BigDecimal;
import java.sql.Date;

public class TransferenciaExterna {

    private int idTransferenciaExterna;
    private int idCuentaOrigen;
    private String bancoDestino;
    private String cuentaDestino;
    private BigDecimal monto;
    private String comprobante;
    private Date fecha;
    private String estado;

    public TransferenciaExterna() {}

    public TransferenciaExterna(int idTransferenciaExterna, int idCuentaOrigen, String bancoDestino,
                                String cuentaDestino, BigDecimal monto, String comprobante,
                                Date fecha, String estado) {
        this.idTransferenciaExterna = idTransferenciaExterna;
        this.idCuentaOrigen = idCuentaOrigen;
        this.bancoDestino = bancoDestino;
        this.cuentaDestino = cuentaDestino;
        this.monto = monto;
        this.comprobante = comprobante;
        this.fecha = fecha;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdTransferenciaExterna() { return idTransferenciaExterna; }
    public void setIdTransferenciaExterna(int idTransferenciaExterna) { this.idTransferenciaExterna = idTransferenciaExterna; }

    public int getIdCuentaOrigen() { return idCuentaOrigen; }
    public void setIdCuentaOrigen(int idCuentaOrigen) { this.idCuentaOrigen = idCuentaOrigen; }

    public String getBancoDestino() { return bancoDestino; }
    public void setBancoDestino(String bancoDestino) { this.bancoDestino = bancoDestino; }

    public String getCuentaDestino() { return cuentaDestino; }
    public void setCuentaDestino(String cuentaDestino) { this.cuentaDestino = cuentaDestino; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public String getComprobante() { return comprobante; }
    public void setComprobante(String comprobante) { this.comprobante = comprobante; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
