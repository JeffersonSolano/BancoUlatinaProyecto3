/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author Usuario
 */
import model.Cuenta;
import java.sql.*;
import java.util.*;

public class ServiceCuenta {

    public boolean crearCuenta(Cuenta c) {
        String sql = "INSERT INTO cuentas_clientes (cedula_cliente, tipo_cuenta, tipo_moneda, saldo, interes, comision, estado_cuenta) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.getCedulaCliente());
            ps.setInt(2, c.getTipo());      
            ps.setInt(3, c.getMoneda());    
            ps.setDouble(4, c.getSaldo());
            ps.setDouble(5, c.getInteres());
            ps.setDouble(6, c.getComision());
            ps.setInt(7, c.getEstado());    

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cuenta> listarCuentasPorCliente(int cedulaCliente) {
        List<Cuenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM cuentas_clientes WHERE cedula_cliente = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cedulaCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Cuenta(
                        //rs.getInt("numeroCuenta"),
                        rs.getInt("cedula_cliente"),
                        rs.getInt("tipo_cuenta"),
                        rs.getInt("tipo_moneda"),
                        rs.getDouble("saldo"),
                        rs.getDouble("interes"),
                        rs.getDouble("comision"),
                        rs.getInt("estado_cuenta")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}