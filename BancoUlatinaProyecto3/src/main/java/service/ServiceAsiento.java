package service;

import model.Asiento;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceAsiento {

    // Listar todos los asientos contables
    public List<Asiento> listarAsientos() {
        List<Asiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM asientos_contables";

        try (Connection con = ConexionBD.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Asiento(
                        rs.getInt("id_asiento"),
                        rs.getInt("id_transaccion"),
                        rs.getString("cuenta_contable"),
                        rs.getDouble("debe"),
                        rs.getDouble("haber"),
                        rs.getDate("fecha") != null ? new Date(rs.getDate("fecha").getTime()) : null
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
