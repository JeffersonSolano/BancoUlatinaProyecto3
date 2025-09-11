/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author Usuario
 */
import model.Cliente;
import service.ConexionBD;
import java.sql.*;
import java.util.*;

public class ServiceCliente {

    // Agregar cliente
    public boolean agregarCliente(Cliente c) {
        String sql = "INSERT INTO Clientes (cedula, nombre, apellido, kyc_verificado) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getCedula());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getApellido());
            ps.setBoolean(4, c.isKycVerificado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Listar clientes
    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";
        try (Connection con = ConexionBD.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Cliente(
                        rs.getInt("cedula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getBoolean("kyc_verificado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean estaEnListaRestrictiva(int cedula) {
        String sql = "SELECT * FROM lista_restriccion WHERE cedula_cliente = ?";
        try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true si existe en lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // ⚠️ en caso de error no bloqueamos (solo mientras depuras)
        }
    }

    public boolean existeCliente(int cedula) {
        String sql = "SELECT 1 FROM clientes WHERE cedula = ?";
        try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // existe si encuentra la fila
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
