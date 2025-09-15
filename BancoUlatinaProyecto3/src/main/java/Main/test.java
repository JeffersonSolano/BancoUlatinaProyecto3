package main;

import model.Cuenta;
import service.ServiceCuenta;
import java.util.Date;
import java.util.List;

public class test {

    public static void main(String[] args) {

        ServiceCuenta service = new ServiceCuenta();

        Cuenta nuevaCuenta = new Cuenta();
        nuevaCuenta.setIdCliente(1111);
        nuevaCuenta.setTipoCuenta("ahorro");
        nuevaCuenta.setMoneda("CRC");
        nuevaCuenta.setSaldo(1000.0);
        nuevaCuenta.setEstado("activa");
        nuevaCuenta.setInteres(0.5);
        nuevaCuenta.setComision(2.0);
        nuevaCuenta.setFechaApertura(new Date());

        boolean ok = service.agregarCuenta(nuevaCuenta);
        System.out.println("Cuenta agregada: " + ok + ", Código: " + nuevaCuenta.getCodigoCuenta());

        List<Cuenta> cuentas = service.listarCuentas();
        cuentas.forEach(c -> {
            System.out.println("ID: " + c.getIdCuentasClientes() +
                    ", Cliente: " + c.getIdCliente() +
                    ", Tipo: " + c.getTipoCuenta() +
                    ", Saldo: " + c.getSaldo() +
                    ", Estado: " + c.getEstado() +
                    ", Fecha: " + c.getFechaApertura() +
                    ", Código: " + c.getCodigoCuenta());
        });
    }
}
