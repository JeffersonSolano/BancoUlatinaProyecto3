package service;

import model.TransferenciaExterna;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

public class ServiceTransferenciaExterna {

    // Listar transferencias externas
    public List<TransferenciaExterna> listarTransferencias() {
        List<TransferenciaExterna> lista = new ArrayList<>();
        String sql = "SELECT * FROM transferencia_externa";

        try (Connection con = ConexionBD.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new TransferenciaExterna(
                        rs.getInt("id_transferencia_externa"),
                        rs.getInt("id_cuenta_origen"),
                        rs.getString("banco_destino"),
                        rs.getString("cuenta_destino"),
                        rs.getBigDecimal("monto"),
                        rs.getString("comprobante"),
                        rs.getDate("fecha"),
                        rs.getString("estado")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Realizar transferencia externa
    public boolean agregarTransferencia(TransferenciaExterna t) {
        String estado = "exitoso";

        try (Connection con = ConexionBD.getConnection()) {
            
            // Verificar saldo de la cuenta origen
            String sqlSaldo = "SELECT saldo FROM cuentas_clientes WHERE id_cuentas_clientes = ?";
            BigDecimal saldoActual = BigDecimal.ZERO;
            try (PreparedStatement ps = con.prepareStatement(sqlSaldo)) {
                ps.setInt(1, t.getIdCuentaOrigen());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        saldoActual = rs.getBigDecimal("saldo");
                    } else {
                        estado = "rechazado";
                    }
                }
            }

            if (saldoActual.compareTo(t.getMonto()) < 0) {
                estado = "rechazado";
            }

            // Actualizar saldo si es suficiente
            if (estado.equals("exitoso")) {
                String sqlActualizar = "UPDATE cuentas_clientes SET saldo = saldo - ? WHERE id_cuentas_clientes = ?";
                try (PreparedStatement ps = con.prepareStatement(sqlActualizar)) {
                    ps.setBigDecimal(1, t.getMonto());
                    ps.setInt(2, t.getIdCuentaOrigen());
                    ps.executeUpdate();
                }
            }

            // Insertar en transferencia_externa
            String sqlInsert = "INSERT INTO transferencia_externa (id_cuenta_origen, banco_destino, cuenta_destino, monto, comprobante, fecha, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {
                ps.setInt(1, t.getIdCuentaOrigen());
                ps.setString(2, t.getBancoDestino());
                ps.setString(3, t.getCuentaDestino());
                ps.setBigDecimal(4, t.getMonto());
                ps.setString(5, t.getComprobante());
                ps.setDate(6, t.getFecha());
                ps.setString(7, estado);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
