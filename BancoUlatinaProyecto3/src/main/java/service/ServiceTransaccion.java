package service;

import model.Transaccion;
import model.Cuenta;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ServiceTransaccion {

    private ServiceCuenta serviceCuenta = new ServiceCuenta(); // Para actualizar saldos

    // Agregar transacción (depósito, retiro, transferencia interna)
    public boolean agregarTransaccion(Transaccion t) {
        boolean exito = false;

        try (Connection con = ConexionBD.getConnection()) {
            con.setAutoCommit(false); // Manejo de transacción

            Cuenta cuentaOrigen = serviceCuenta.obtenerCuentaPorId(t.getIdCuentaOrigen());
            Cuenta cuentaDestino = t.getIdCuentaDestino() != 0 
                                   ? serviceCuenta.obtenerCuentaPorId(t.getIdCuentaDestino()) 
                                   : null;

            // Validar saldo suficiente para retiro o transferencia
            if ((t.getTipo().equalsIgnoreCase("retiro") || t.getTipo().equalsIgnoreCase("transferencia")) 
                && cuentaOrigen.getSaldo().compareTo(t.getMonto()) < 0) {

                t.setEstado("rechazado");
                insertarTransaccion(con, t);
                con.commit();
                return false; // Dinero insuficiente
            }

            // Actualizar saldos según tipo
            switch (t.getTipo().toLowerCase()) {
                case "deposito":
                    serviceCuenta.actualizarSaldo(con, cuentaOrigen.getIdCuentasClientes(), 
                                                  cuentaOrigen.getSaldo().add(t.getMonto()));
                    t.setEstado("exitoso");
                    break;

                case "retiro":
                    serviceCuenta.actualizarSaldo(con, cuentaOrigen.getIdCuentasClientes(), 
                                                  cuentaOrigen.getSaldo().subtract(t.getMonto()));
                    t.setEstado("exitoso");
                    break;

                case "transferencia":
                    // Debe haber cuenta destino válida
                    if (cuentaDestino != null) {
                        serviceCuenta.actualizarSaldo(con, cuentaOrigen.getIdCuentasClientes(),
                                                      cuentaOrigen.getSaldo().subtract(t.getMonto()));
                        serviceCuenta.actualizarSaldo(con, cuentaDestino.getIdCuentasClientes(),
                                                      cuentaDestino.getSaldo().add(t.getMonto()));
                        t.setEstado("exitoso");
                    } else {
                        t.setEstado("rechazado");
                    }
                    break;
            }

            // Insertar transacción
            int idTransaccion = insertarTransaccion(con, t);
            t.setIdTransaccion(idTransaccion);

            // Generar asiento contable automáticamente si fue exitosa
            if (t.getEstado().equals("exitoso")) {
                generarAsientoContable(con, t);
            }

            con.commit();
            exito = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }

    // Método para insertar la transacción y retornar el id generado
    private int insertarTransaccion(Connection con, Transaccion t) throws SQLException {
        String sql = "INSERT INTO transacciones (id_cuenta_origen, id_cuenta_destino, tipo, monto, fecha, estado, autorizacion, descripcion) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getIdCuentaOrigen());
            if (t.getIdCuentaDestino() != 0)
                ps.setInt(2, t.getIdCuentaDestino());
            else
                ps.setNull(2, Types.INTEGER);

            ps.setString(3, t.getTipo());
            ps.setBigDecimal(4, t.getMonto());
            ps.setDate(5, t.getFecha() != null ? t.getFecha() : new Date(System.currentTimeMillis()));
            ps.setString(6, t.getEstado());
            ps.setBoolean(7, t.isAutorizacion());
            ps.setString(8, t.getDescripcion());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    // Método para generar los asientos contables automáticamente
    private void generarAsientoContable(Connection con, Transaccion t) throws SQLException {
        String sql = "INSERT INTO asientos_contables (id_transaccion, cuenta_contable, debe, haber, fecha) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            Date fecha = t.getFecha() != null ? t.getFecha() : new Date(System.currentTimeMillis());

            switch (t.getTipo().toLowerCase()) {
                case "deposito":
                    // Debe: Caja, Haber: Cuenta Cliente
                    ps.setInt(1, t.getIdTransaccion());
                    ps.setString(2, "Caja");
                    ps.setBigDecimal(3, t.getMonto());
                    ps.setBigDecimal(4, BigDecimal.ZERO);
                    ps.setDate(5, fecha);
                    ps.executeUpdate();

                    ps.setString(2, "Cuenta Cliente #" + t.getIdCuentaOrigen());
                    ps.setBigDecimal(3, BigDecimal.ZERO);
                    ps.setBigDecimal(4, t.getMonto());
                    ps.setDate(5, fecha);
                    ps.executeUpdate();
                    break;

                case "retiro":
                    // Debe: Cuenta Cliente, Haber: Caja
                    ps.setInt(1, t.getIdTransaccion());
                    ps.setString(2, "Cuenta Cliente #" + t.getIdCuentaOrigen());
                    ps.setBigDecimal(3, t.getMonto());
                    ps.setBigDecimal(4, BigDecimal.ZERO);
                    ps.setDate(5, fecha);
                    ps.executeUpdate();

                    ps.setString(2, "Caja");
                    ps.setBigDecimal(3, BigDecimal.ZERO);
                    ps.setBigDecimal(4, t.getMonto());
                    ps.setDate(5, fecha);
                    ps.executeUpdate();
                    break;

                case "transferencia":
                    // Debe: Cuenta Destino, Haber: Cuenta Origen
                    ps.setInt(1, t.getIdTransaccion());
                    ps.setString(2, "Cuenta Cliente #" + t.getIdCuentaDestino());
                    ps.setBigDecimal(3, t.getMonto());
                    ps.setBigDecimal(4, BigDecimal.ZERO);
                    ps.setDate(5, fecha);
                    ps.executeUpdate();

                    ps.setString(2, "Cuenta Cliente #" + t.getIdCuentaOrigen());
                    ps.setBigDecimal(3, BigDecimal.ZERO);
                    ps.setBigDecimal(4, t.getMonto());
                    ps.setDate(5, fecha);
                    ps.executeUpdate();
                    break;
            }
        }
    }

    // Listar todas las transacciones
    public List<Transaccion> listarTransacciones() {
        List<Transaccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM transacciones";
        try (Connection con = ConexionBD.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Transaccion(
                        rs.getInt("id_transacciones"),
                        rs.getInt("id_cuenta_origen"),
                        rs.getInt("id_cuenta_destino"),
                        rs.getString("tipo"),
                        rs.getBigDecimal("monto"),
                        rs.getDate("fecha"),
                        rs.getString("estado"),
                        rs.getBoolean("autorizacion"),
                        rs.getString("descripcion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
