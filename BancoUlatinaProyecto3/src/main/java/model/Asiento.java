package model;

import java.util.Date;

public class Asiento {

    private int idAsiento;
    private int idTransaccion;
    private String cuentaContable;
    private Double debe;
    private Double haber;
    private Date fecha;

    // Constructor vacío
    public Asiento() {}

    // Constructor con todos los campos
    public Asiento(int idAsiento, int idTransaccion, String cuentaContable, 
                   Double debe, Double haber, Date fecha) {
        this.idAsiento = idAsiento;
        this.idTransaccion = idTransaccion;
        this.cuentaContable = cuentaContable;
        this.debe = debe;
        this.haber = haber;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getCuentaContable() {
        return cuentaContable;
    }

    public void setCuentaContable(String cuentaContable) {
        this.cuentaContable = cuentaContable;
    }

    public Double getDebe() {
        return debe;
    }

    public void setDebe(Double debe) {
        this.debe = debe;
    }

    public Double getHaber() {
        return haber;
    }

    public void setHaber(Double haber) {
        this.haber = haber;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
