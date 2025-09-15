package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Cuenta {

    private int idCuentasClientes;  // PK interna
    private int idCliente;          // FK hacia clientes.cedula
    private String tipoCuenta;      // ahorro / corriente
    private String moneda;          // CRC / USD
    private Double saldo;
    private String estado;          // activa / bloqueada / cerrada
    private Double interes;
    private Double comision;
    private java.util.Date fechaApertura;
    private String codigoCuenta;    // Identificador por cliente (001, 002, ...)

    public Cuenta() {
    }

    public Cuenta(int idCuentasClientes, int idCliente, String tipoCuenta, String moneda, Double saldo, String estado, Double interes, Double comision, java.util.Date fechaApertura, String codigoCuenta) {
        this.idCuentasClientes = idCuentasClientes;
        this.idCliente = idCliente;
        this.tipoCuenta = tipoCuenta;
        this.moneda = moneda;
        this.saldo = saldo;
        this.estado = estado;
        this.interes = interes;
        this.comision = comision;
        this.fechaApertura = fechaApertura;
        this.codigoCuenta = codigoCuenta;
    }

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

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public java.util.Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(java.util.Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

   
    
}
