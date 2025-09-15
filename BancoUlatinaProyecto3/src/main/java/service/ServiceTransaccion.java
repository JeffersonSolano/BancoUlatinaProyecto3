package service;

import model.Transaccion;
import model.Cuenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceTransaccion {

    private ServiceCuenta serviceCuenta = new ServiceCuenta(); // Para actualizar saldos y buscar cuentas

    // Agregar transacción (depósito, retiro, transferencia interna)
    public boolean agregarTransaccion(Transaccion t) {
        boolean exito = false;

        try (Connection con = ConexionBD.getConnection()) {
            con.setAutoCommit(false); // Manejo de transacción

            // Obtener cuentas por código
            Cuenta cuentaOrigen = serviceCuenta.obtenerCuentaPorCodigo(t.getCodigoCuentaOrigen());
            Cuenta cuentaDestino = t.getCodigoCuentaDestino() != null && !t.getCodigoCuentaDestino().isEmpty()
                    ? serviceCuenta.obtenerCuentaPorCodigo(t.getCodigoCuentaDestino())
                    : null;

            if (cuentaOrigen == null) {
                System.out.println("Cuenta de origen no encontrada: " + t.getCodigoCuentaOrigen());
                return false;
            }

            t.setIdCuentaOrigen(cuentaOrigen.getIdCuentasClientes());
            if (cuentaDestino != null) {
                t.setIdCuentaDestino(cuentaDestino.getIdCuentasClientes());
            } else {
                t.setIdCuentaDestino(0);
            }

            // Validar saldo suficiente para retiro o transferencia
            if ((t.getTipo().equalsIgnoreCase("retiro") || t.getTipo().equalsIgnoreCase("transferencia"))
                    && cuentaOrigen.getSaldo() < t.getMonto()) {

                t.setEstado("rechazado");
                insertarTransaccion(con, t);
                con.commit();
                return false; // Dinero insuficiente
            }

            // Actualizar saldos según tipo
            switch (t.getTipo().toLowerCase()) {
                case "deposito":
                    serviceCuenta.actualizarSaldo(con, cuentaOrigen.getIdCuentasClientes(),
                            cuentaOrigen.getSaldo() + t.getMonto());
                    t.setEstado("exitoso");
                    break;

                case "retiro":
                    serviceCuenta.actualizarSaldo(con, cuentaOrigen.getIdCuentasClientes(),
                            cuentaOrigen.getSaldo() - t.getMonto());
                    t.setEstado("exitoso");
                    break;

                case "transferencia":
                    if (cuentaDestino != null) {
                        serviceCuenta.actualizarSaldo(con, cuentaOrigen.getIdCuentasClientes(),
                                cuentaOrigen.getSaldo() - t.getMonto());
                        serviceCuenta.actualizarSaldo(con, cuentaDestino.getIdCuentasClientes(),
                                cuentaDestino.getSaldo() + t.getMonto());
                        t.setEstado("exitoso");
                    } else {
                        t.setEstado("rechazado");
                    }
                    break;
                default:
                    t.setEstado("rechazado");
            }

            // Insertar transacción
            int idTransaccion = insertarTransaccion(con, t);
            t.setIdTransaccion(idTransaccion);

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
            if (t.getIdCuentaDestino() != 0) {
                ps.setInt(2, t.getIdCuentaDestino());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            ps.setString(3, t.getTipo());
            ps.setDouble(4, t.getMonto());
            ps.setDate(5, new java.sql.Date(t.getFecha() != null ? t.getFecha().getTime() : System.currentTimeMillis()));
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

    // Listar todas las transacciones
    public List<Transaccion> listarTransacciones() {
        List<Transaccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM transacciones";
        try (Connection con = ConexionBD.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Transaccion(
                        rs.getInt("id_transacciones"),
                        rs.getInt("id_cuenta_origen"),
                        rs.getInt("id_cuenta_destino"),
                        null, // código desde la vista no es necesario al listar
                        null,
                        rs.getString("tipo"),
                        rs.getDouble("monto"),
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
