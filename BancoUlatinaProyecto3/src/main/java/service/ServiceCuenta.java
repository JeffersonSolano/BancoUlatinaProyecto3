package service;

import model.Cuenta;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

public class ServiceCuenta {

    // Agregar nueva cuenta
    public boolean agregarCuenta(Cuenta c) {
        // Generar código de cuenta automáticamente antes de insertar
        c.setCodigoCuenta(generarCodigoCuenta()); // sin pasar idCliente

        String sql = "INSERT INTO cuentas_clientes "
                + "(id_clientes, tipo_cuenta, moneda, saldo, estado, interes, comision, fecha_apertura, codigoCuenta) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.getIdCliente());
            ps.setString(2, c.getTipoCuenta());
            ps.setString(3, c.getMoneda());
            ps.setBigDecimal(4, BigDecimal.valueOf(c.getSaldo()));
            ps.setString(5, c.getEstado());
            ps.setBigDecimal(6, BigDecimal.valueOf(c.getInteres()));
            ps.setBigDecimal(7, BigDecimal.valueOf(c.getComision()));
            ps.setDate(8, new java.sql.Date(c.getFechaApertura().getTime()));
            ps.setString(9, c.getCodigoCuenta());

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
                        rs.getString("tipo_cuenta"),
                        rs.getString("moneda"),
                        rs.getDouble("saldo"),
                        rs.getString("estado"),
                        rs.getDouble("interes"),
                        rs.getDouble("comision"),
                        rs.getDate("fecha_apertura"),
                        rs.getString("codigoCuenta")
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
                            rs.getString("tipo_cuenta"),
                            rs.getString("moneda"),
                            rs.getDouble("saldo"),
                            rs.getString("estado"),
                            rs.getDouble("interes"),
                            rs.getDouble("comision"),
                            rs.getDate("fecha_apertura"),
                            rs.getString("codigoCuenta")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public String generarCodigoCuenta() {
        String sql = "SELECT COUNT(*) AS total FROM cuentas_clientes";
        try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int count = rs.getInt("total") + 1;
                return String.format("%03d", count); // 001, 002, 003, ...
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "001"; // fallback
    }

    // Obtener cuenta por ID
    public Cuenta obtenerCuentaPorId(int idCuenta) {
        String sql = "SELECT * FROM cuentas_clientes WHERE id_cuentas_clientes = ?";

        try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCuenta);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cuenta(
                            rs.getInt("id_cuentas_clientes"),
                            rs.getInt("id_clientes"),
                            rs.getString("tipo_cuenta"),
                            rs.getString("moneda"),
                            rs.getDouble("saldo"),
                            rs.getString("estado"),
                            rs.getDouble("interes"),
                            rs.getDouble("comision"),
                            rs.getDate("fecha_apertura"),
                            rs.getString("codigoCuenta")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarSaldo(Connection con, int idCuenta, Double nuevoSaldo) throws SQLException {
        String sql = "UPDATE cuentas_clientes SET saldo = ? WHERE id_cuentas_clientes = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBigDecimal(1, BigDecimal.valueOf(nuevoSaldo));
            ps.setInt(2, idCuenta);
            return ps.executeUpdate() > 0;
        }
    }
    
    // Dentro de ServiceCuenta
public Cuenta obtenerCuentaPorCodigo(String codigoCuenta) {
    String sql = "SELECT * FROM cuentas_clientes WHERE codigoCuenta = ?";
    try (Connection con = ConexionBD.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, codigoCuenta);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Cuenta(
                        rs.getInt("id_cuentas_clientes"),
                        rs.getInt("id_clientes"),
                        rs.getString("tipo_cuenta"),
                        rs.getString("moneda"),
                        rs.getDouble("saldo"),
                        rs.getString("estado"),
                        rs.getDouble("interes"),
                        rs.getDouble("comision"),
                        rs.getDate("fecha_apertura"),
                        rs.getString("codigoCuenta")
                );
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null; // si no encuentra nada
}

    
    
    
    
}
