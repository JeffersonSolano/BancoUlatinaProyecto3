package main;

import model.Transaccion;
import service.ServiceTransaccion;
import java.util.Date;

public class test {

    public static void main(String[] args) {

        ServiceTransaccion serviceTransaccion = new ServiceTransaccion();

        // Deposito
        Transaccion deposito = new Transaccion();
        deposito.setCodigoCuentaOrigen("001"); // Código de la cuenta que recibe
        deposito.setTipo("deposito");
        deposito.setMonto(50000.0);
        deposito.setFecha(new Date());
        deposito.setDescripcion("Depósito inicial");
        deposito.setAutorizacion(true);

        serviceTransaccion.agregarTransaccion(deposito);

        // Retiro
        Transaccion retiro = new Transaccion();
        retiro.setCodigoCuentaOrigen("001");
        retiro.setTipo("retiro");
        retiro.setMonto(20000.0);
        retiro.setFecha(new Date());
        retiro.setDescripcion("Retiro cajero");
        retiro.setAutorizacion(true);

        serviceTransaccion.agregarTransaccion(retiro);

        // Transferencia interna
        Transaccion transferencia = new Transaccion();
        transferencia.setCodigoCuentaOrigen("001");
        transferencia.setCodigoCuentaDestino("002");
        transferencia.setTipo("transferencia");
        transferencia.setMonto(10000.0);
        transferencia.setFecha(new Date());
        transferencia.setDescripcion("Transferencia ahorro -> corriente");
        transferencia.setAutorizacion(true);

        serviceTransaccion.agregarTransaccion(transferencia);

        System.out.println("Transacciones registradas correctamente.");
    }
}
