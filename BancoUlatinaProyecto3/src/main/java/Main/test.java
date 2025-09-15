package main;

import model.Cuenta;
import service.ServiceCuenta;

import java.sql.Date;
import java.util.List;

public class test {

    public static void main(String[] args) {

        // Crear el servicio para cuentas
        ServiceCuenta service = new ServiceCuenta();

        // 1) Crear una nueva cuenta para un cliente existente
        Cuenta nuevaCuenta = new Cuenta();
        nuevaCuenta.setIdCliente(1221);              // asegúrate que exista este cliente
        nuevaCuenta.setTipoCuenta("ahorro");             // ENUM: 'ahorro' o 'corriente'
        nuevaCuenta.setMoneda("CRC");
        nuevaCuenta.setSaldo(1000.00);                   // ahora Double
        nuevaCuenta.setEstado("Activa");
        nuevaCuenta.setInteres(0.5);                     // ahora Double
        nuevaCuenta.setComision(2.0);                    // ahora Double
        nuevaCuenta.setFechaApertura(new Date(System.currentTimeMillis()));

        // 2) Insertar la cuenta en la base de datos
        boolean insertOk = service.agregarCuenta(nuevaCuenta);
        System.out.println("Cuenta insertada correctamente? " + insertOk);

        // 3) Listar todas las cuentas y mostrar
        List<Cuenta> cuentas = service.listarCuentas();
        System.out.println("\n--- Lista de cuentas ---");
        for (Cuenta c : cuentas) {
            System.out.println("ClienteID (cédula): " + c.getIdCliente()
                    + ", Tipo: " + c.getTipoCuenta()
                    + ", Moneda: " + c.getMoneda()
                    + ", Saldo: " + c.getSaldo()
                    + ", Estado: " + c.getEstado()
                    + ", Código: " + c.getCodigoCuenta());
        }
    }
}
