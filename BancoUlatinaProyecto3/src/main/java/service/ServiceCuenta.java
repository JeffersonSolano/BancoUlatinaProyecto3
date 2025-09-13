package service;

import model.Cuenta;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

public class ServiceCuenta {

    // Agregar nueva cuenta
    public boolean agregarCuenta(Cuenta c) {
        // Generar código de cuenta automáticamente antes de insertar
        c.setCodigoCuenta(generarCodigoCuenta(c.getIdCliente()));

        String sql = "INSERT INTO cuentas_clientes "
                + "(id_clientes, tipo_cuenta, moneda, saldo, estado, interes, comision, fecha_apertura, codigo_cuenta) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.getIdCliente());          // cédula del cliente
            ps.setString(2, c.getTipoCuenta());
            ps.setString(3, c.getMoneda());
            ps.setBigDecimal(4, c.getSaldo());
            ps.setString(5, c.getEstado());
            ps.setBigDecimal(6, c.getInteres());
            ps.setBigDecimal(7, c.getComision());
            ps.setDate(8, c.getFechaApertura());
            ps.setString(9, c.getCodigoCuenta());   // generado automáticamente

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Listar todas las cuentas
    public List<Cuenta> listarCuentas() {
        List<Cuenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM cuentas_clientes";
        try (Connection con = ConexionBD.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Cuenta(
                        rs.getInt("id_cuentas_clientes"),
                        rs.getInt("id_clientes"),
                        rs.getString("codigo_cuenta"), // 🔹 ahora va en la posición correcta
                        rs.getString("tipo_cuenta"),
                        rs.getString("moneda"),
                        rs.getBigDecimal("saldo"),
                        rs.getString("estado"),
                        rs.getBigDecimal("interes"),
                        rs.getBigDecimal("comision"),
                        rs.getDate("fecha_apertura")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Listar cuentas de un cliente específico
    public List<Cuenta> listarCuentasPorCliente(int idCliente) {
        List<Cuenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM cuentas_clientes WHERE id_clientes = ?";
        try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Cuenta(
                            rs.getInt("id_cuentas_clientes"),
                            rs.getInt("id_clientes"),
                            rs.getString("codigo_cuenta"), // 🔹 ahora va en la posición correcta
                            rs.getString("tipo_cuenta"),
                            rs.getString("moneda"),
                            rs.getBigDecimal("saldo"),
                            rs.getString("estado"),
                            rs.getBigDecimal("interes"),
                            rs.getBigDecimal("comision"),
                            rs.getDate("fecha_apertura")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Generar un código de cuenta único por cliente (ej. 001, 002, 003)
    public String generarCodigoCuenta(int idCliente) {
        String sql = "SELECT COUNT(*) AS total FROM cuentas_clientes WHERE id_clientes = ?";
        try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("total") + 1;
                    return String.format("%03d", count); // 001, 002, 003
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "001"; // fallback
    }

    //metodos para transacciones
    public Cuenta obtenerCuentaPorId(int idCuenta) {
        String sql = "SELECT * FROM cuentas_clientes WHERE id_cuentas_clientes = ?";
        try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCuenta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cuenta(
                            rs.getInt("id_cuentas_clientes"),
                            rs.getInt("id_clientes"),
                            rs.getString("codigo_cuenta"), // 🔹 ahora va en la posición correcta
                            rs.getString("tipo_cuenta"),
                            rs.getString("moneda"),
                            rs.getBigDecimal("saldo"),
                            rs.getString("estado"),
                            rs.getBigDecimal("interes"),
                            rs.getBigDecimal("comision"),
                            rs.getDate("fecha_apertura") // si agregaste este campo
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarSaldo(Connection con, int idCuenta, BigDecimal nuevoSaldo) throws SQLException {
        String sql = "UPDATE cuentas_clientes SET saldo = ? WHERE id_cuentas_clientes = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBigDecimal(1, nuevoSaldo);
            ps.setInt(2, idCuenta);
            return ps.executeUpdate() > 0;
        }
    }

}
