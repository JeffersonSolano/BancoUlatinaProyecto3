/*package main;

import service.ServicePrestamo;
import model.Prestamo;
import java.util.Date;
import java.util.List;

public class test {
    public static void main(String[] args) {

        ServicePrestamo service = new ServicePrestamo();

        // 1. Crear un préstamo de prueba
        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setIdCliente(12345678); // cedula del cliente
        nuevoPrestamo.setTipo("personal");
        nuevoPrestamo.setMonto(10000000.0); // ₡10 millones
        nuevoPrestamo.setPlazoMeses(60); // 5 años
        nuevoPrestamo.setTasaInteres(12.5); // interés anual
        nuevoPrestamo.setFechaSolicitud(new Date()); // fecha actual
        nuevoPrestamo.setEstado("activo");

        boolean agregado = service.agregarPrestamo(nuevoPrestamo);
        if (agregado) {
            System.out.println("Préstamo agregado correctamente. ID generado: " + nuevoPrestamo.getIdPrestamo());
        } else {
            System.out.println("Error al agregar el préstamo.");
        }

        // 2. Listar todos los préstamos
        System.out.println("\nListado de todos los préstamos:");
        List<Prestamo> prestamos = service.listarPrestamos();
        for (Prestamo p : prestamos) {
            System.out.println("ID: " + p.getIdPrestamo() +
                               ", Cliente: " + p.getIdCliente() +
                               ", Tipo: " + p.getTipo() +
                               ", Monto: " + p.getMonto() +
                               ", Plazo: " + p.getPlazoMeses() +
                               ", Tasa: " + p.getTasaInteres() +
                               ", Fecha: " + p.getFechaSolicitud() +
                               ", Estado: " + p.getEstado());
        }

        // 3. Actualizar estado del préstamo (ejemplo)
        if (!prestamos.isEmpty()) {
            int idActualizar = prestamos.get(0).getIdPrestamo();
            boolean actualizado = service.actualizarEstado(idActualizar, "mora");
            System.out.println("\nActualización del estado del préstamo con ID " + idActualizar + ": " + (actualizado ? "Éxito" : "Error"));
        }
    }
}*/
