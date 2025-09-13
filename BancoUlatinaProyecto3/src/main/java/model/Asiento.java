package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Asiento {

    private int idAsiento;
    private int idTransaccion;
    private String cuentaContable;
    private BigDecimal debe;
    private BigDecimal haber;
    private Date fecha;

    // Constructor vacío
    public Asiento() {}

    // Constructor con todos los campos
    public Asiento(int idAsiento, int idTransaccion, String cuentaContable, 
                   BigDecimal debe, BigDecimal haber, Date fecha) {
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

    public BigDecimal getDebe() {
        return debe;
    }

    public void setDebe(BigDecimal debe) {
        this.debe = debe;
    }

    public BigDecimal getHaber() {
        return haber;
    }

    public void setHaber(BigDecimal haber) {
        this.haber = haber;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
