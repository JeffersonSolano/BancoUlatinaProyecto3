package service;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import model.Prestamo;
import model.Cuota;

public class ServicePrestamo {

    // Agregar un nuevo préstamo y generar cuotas automáticamente
    public boolean agregarPrestamo(Prestamo p) {
        String sqlPrestamo = "INSERT INTO prestamos "
                + "(id_cliente, tipo, monto, plazo_meses, tasa_interes, fecha_solicitud, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement psPrestamo = null;
        ResultSet rsKeys = null;

        try {
            con = ConexionBD.getConnection();
            con.setAutoCommit(false); // transacción

            // Registrar préstamo
            psPrestamo = con.prepareStatement(sqlPrestamo, Statement.RETURN_GENERATED_KEYS);
            psPrestamo.setInt(1, p.getIdCliente());
            psPrestamo.setString(2, p.getTipo());
            psPrestamo.setBigDecimal(3, p.getMonto());
            psPrestamo.setInt(4, p.getPlazoMeses());
            psPrestamo.setBigDecimal(5, p.getTasaInteres());
            psPrestamo.setDate(6, new java.sql.Date(System.currentTimeMillis())); // fecha actual
            psPrestamo.setString(7, "pendiente");
            int affectedRows = psPrestamo.executeUpdate();

            if (affectedRows == 0) {
                con.rollback();
                return false;
            }

            // Obtener ID generado
            rsKeys = psPrestamo.getGeneratedKeys();
            int idPrestamo = 0;
            if (rsKeys.next()) {
                idPrestamo = rsKeys.getInt(1);
            } else {
                con.rollback();
                return false;
            }

            // Generar cuotas (amortización simple con cuota fija)
            generarCuotas(con, idPrestamo, p);

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try { if (rsKeys != null) rsKeys.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (psPrestamo != null) psPrestamo.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (con != null) con.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    // Generar cuotas del préstamo
    private void generarCuotas(Connection con, int idPrestamo, Prestamo p) throws SQLException {
        String sqlCuota = "INSERT INTO cuotas (id_prestamo, nro_cuota, monto_cuota, fecha_vencimiento, estado) "
                        + "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement psCuota = con.prepareStatement(sqlCuota);

        BigDecimal monto = p.getMonto();
        int plazo = p.getPlazoMeses();
        BigDecimal tasaMensual = p.getTasaInteres().divide(new java.math.BigDecimal(12 * 100), 10, BigDecimal.ROUND_HALF_UP);

        // Cuota fija (sistema francés)
        BigDecimal factor = (tasaMensual.add(BigDecimal.ONE).pow(plazo).multiply(tasaMensual))
                .divide((BigDecimal.ONE.add(tasaMensual).pow(plazo)).subtract(BigDecimal.ONE), 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal cuotaFija = monto.multiply(factor).setScale(2, BigDecimal.ROUND_HALF_UP);

        Calendar cal = Calendar.getInstance();
        for (int i = 1; i <= plazo; i++) {
            cal.add(Calendar.MONTH, 1);
            psCuota.setInt(1, idPrestamo);
            psCuota.setInt(2, i);
            psCuota.setBigDecimal(3, cuotaFija);
            psCuota.setDate(4, new java.sql.Date(cal.getTimeInMillis()));
            psCuota.setString(5, "pendiente");
            psCuota.addBatch();
        }
        psCuota.executeBatch();
        psCuota.close();
    }

    // Listar todos los préstamos
    public List<Prestamo> listarPrestamos() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos ORDER BY id_prestamo DESC";
        try (Connection con = ConexionBD.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Prestamo p = new Prestamo(
                        rs.getInt("id_prestamo"),
                        rs.getInt("id_cliente"),
                        rs.getString("tipo"),
                        rs.getBigDecimal("monto"),
                        rs.getInt("plazo_meses"),
                        rs.getBigDecimal("tasa_interes"),
                        rs.getDate("fecha_solicitud"),
                        rs.getString("estado")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
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
                            rs.getInt("id_cuota"),
                            rs.getInt("id_prestamo"),
                            rs.getInt("nro_cuota"),
                            rs.getBigDecimal("monto_cuota"),
                            rs.getDate("fecha_vencimiento"),
                            rs.getString("estado")
                    );
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
