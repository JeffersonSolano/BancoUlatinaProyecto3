/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Usuario
 */
public class Cuenta implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int cedulaCliente;
    private int tipo; // Ahorro o Corriente
    private int moneda; // CRC o USD
    private double saldo;
    private double interes; // % anual
    private double comision; // % o monto fijo
    private int estado; // Activa, Bloqueada, Cerrada

    public Cuenta(int cedulaCliente, int tipo, int moneda, double saldo, double interes, double comision, int estado) {
        this.cedulaCliente = cedulaCliente;
        this.tipo = tipo;
        this.moneda = moneda;
        this.saldo = saldo;
        this.interes = interes;
        this.comision = comision;
        this.estado = estado;
    }

    public Cuenta() {
    }

    public int getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(int cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getMoneda() {
        return moneda;
    }

    public void setMoneda(int moneda) {
        this.moneda = moneda;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

}