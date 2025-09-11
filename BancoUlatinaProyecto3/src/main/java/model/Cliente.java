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

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private int cedula;
    private String nombre;
    private String apellido;
    private boolean kycVerificado;

    public Cliente(int cedula, String nombre, String apellido, boolean kycVerificado) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.kycVerificado = kycVerificado;
    }

    public Cliente() {
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public boolean isKycVerificado() {
        return kycVerificado;
    }

    public void setKycVerificado(boolean kycVerificado) {
        this.kycVerificado = kycVerificado;
    }

   
    
}
