package service;

import model.Prestamo;
import model.Cuota;
import java.sql.*;
import java.util.*;

public class ServicePrestamo {

    // Agregar un nuevo préstamo y generar cuotas automáticamente
    public boolean agregarPrestamo(Prestamo p) {
        String sqlPrestamo = "INSERT INTO prestamos "
                + "(id_cliente, tipo, monto, plazo_meses, tasa_interes, fecha_solicitud, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement psPrestamo = con.prepareStatement(sqlPrestamo, Statement.RETURN_GENERATED_KEYS)) {

            // Asegurarse de que fecha y estado estén inicializados
            if (p.getFechaSolicitud() == null) {
                p.setFechaSolicitud(new java.util.Date());
            }
            if (p.getEstado() == null || p.getEstado().isEmpty()) {
                p.setEstado("activo");
            }

            psPrestamo.setInt(1, p.getIdCliente());
            psPrestamo.setString(2, p.getTipo());
            psPrestamo.setDouble(3, p.getMonto());
            psPrestamo.setInt(4, p.getPlazoMeses());
            psPrestamo.setDouble(5, p.getTasaInteres());
            psPrestamo.setDate(6, new java.sql.Date(p.getFechaSolicitud().getTime()));
            psPrestamo.setString(7, p.getEstado());

            int affectedRows = psPrestamo.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("No se pudo insertar el préstamo.");
                return false;
            }

            // Obtener ID generado
            int idPrestamo = 0;
            try (ResultSet rs = psPrestamo.getGeneratedKeys()) {
                if (rs.next()) idPrestamo = rs.getInt(1);
            }

            // Generar cuotas automáticamente
            generarCuotas(con, idPrestamo, p);

            return true;

        } catch (SQLException e) {
            System.err.println("Error al insertar préstamo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Generar cuotas (sistema francés, cuotas fijas)
    private void generarCuotas(Connection con, int idPrestamo, Prestamo p) throws SQLException {
        String sqlCuota = "INSERT INTO cuotas (id_prestamo, nro_cuota, fecha_vencimiento, monto_cuota, estado) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sqlCuota)) {
            double monto = p.getMonto();
            int plazo = p.getPlazoMeses();
            double tasaMensual = p.getTasaInteres() / 12 / 100;

            // Fórmula de cuota fija
            double factor = (tasaMensual * Math.pow(1 + tasaMensual, plazo)) /
                            (Math.pow(1 + tasaMensual, plazo) - 1);
            double cuotaFija = Math.round(monto * factor * 100.0) / 100.0;

            Calendar cal = Calendar.getInstance();

            for (int i = 1; i <= plazo; i++) {
                cal.add(Calendar.MONTH, 1);
                ps.setInt(1, idPrestamo);
                ps.setInt(2, i);
                ps.setDate(3, new java.sql.Date(cal.getTimeInMillis()));
                ps.setDouble(4, cuotaFija);
                ps.setString(5, "pendiente");
                ps.addBatch();
                System.out.println("Generando cuota " + i + " para préstamo " + idPrestamo + " monto: " + cuotaFija);

            }
            ps.executeBatch();
            System.out.println("ID Prestamo generado: " + idPrestamo);

        }
    }

    // Listar todos los préstamos
    public List<Prestamo> listarPrestamos() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos ORDER BY id_prestamos DESC";

        try (Connection con = ConexionBD.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Prestamo p = new Prestamo(
                        rs.getInt("id_prestamos"),
                        rs.getInt("id_cliente"),
                        rs.getString("tipo"),
                        rs.getDouble("monto"),
                        rs.getInt("plazo_meses"),
                        rs.getDouble("tasa_interes"),
                        rs.getDate("fecha_solicitud"),
                        rs.getString("estado")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar préstamos: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // Listar cuotas de un préstamo
    public List<Cuota> listarCuotas(int idPrestamo) {
        List<Cuota> lista = new ArrayList<>();
        String sql = "SELECT * FROM cuotas WHERE id_prestamo = ? ORDER BY nro_cuota ASC";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPrestamo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cuota c = new Cuota(
                            rs.getInt("id_cuotas"),
                            rs.getInt("id_prestamo"),
                            rs.getInt("nro_cuota"),
                            rs.getDate("fecha_vencimiento"),
                            rs.getDouble("monto_cuota"),
                            rs.getString("estado")
                    );
                    lista.add(c);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al listar cuotas: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }
}
