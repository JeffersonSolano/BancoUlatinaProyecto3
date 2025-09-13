package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Cuenta {

    private int idCuentasClientes;  // PK interna
    private int idCliente;          // FK hacia clientes.cedula
    private String codigoCuenta;    // Identificador por cliente (001, 002, ...)
    private String tipoCuenta;      // ahorro / corriente
    private String moneda;          // CRC / USD
    private BigDecimal saldo;
    private String estado;          // activa / bloqueada / cerrada
    private BigDecimal interes;
    private BigDecimal comision;
    private Date fechaApertura;

    // Constructor vacío
    public Cuenta() {}

    // Constructor completo
    public Cuenta(int idCuentasClientes, int idCliente, String codigoCuenta,
                  String tipoCuenta, String moneda, BigDecimal saldo,
                  String estado, BigDecimal interes, BigDecimal comision,
                  Date fechaApertura) {
        this.idCuentasClientes = idCuentasClientes;
        this.idCliente = idCliente;
        this.codigoCuenta = codigoCuenta;
        this.tipoCuenta = tipoCuenta;
        this.moneda = moneda;
        this.saldo = saldo;
        this.estado = estado;
        this.interes = interes;
        this.comision = comision;
        this.fechaApertura = fechaApertura;
    }

    // Getters y setters
    public int getIdCuentasClientes() {
        return idCuentasClientes;
    }

    public void setIdCuentasClientes(int idCuentasClientes) {
        this.idCuentasClientes = idCuentasClientes;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }
}
