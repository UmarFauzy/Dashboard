package dao;

import model.Kurir;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KurirDAO {
    private Connection connection;

    public KurirDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void insertKurir(Kurir kurir) throws SQLException {
        String query = "INSERT INTO kurir (Nama_Kurir, Total_Sampah, Total_Point) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, kurir.getNamaKurir());
            stmt.setInt(2, kurir.getTotalSampah());
            stmt.setInt(3, kurir.getTotalPoint());
            stmt.executeUpdate();
        }
    }

    // READ
    public List<Kurir> getAllKurir() throws SQLException {
        List<Kurir> kurirList = new ArrayList<>();
        String query = "SELECT * FROM kurir";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                kurirList.add(new Kurir(
                        rs.getInt("ID"),
                        rs.getString("Nama_Kurir"),
                        rs.getInt("Total_Sampah"),
                        rs.getInt("Total_Point")
                ));
            }
        }
        return kurirList;
    }

    // UPDATE
    public void updateKurir(Kurir kurir) throws SQLException {
        String query = "UPDATE kurir SET Nama_Kurir = ?, Total_Sampah = ?, Total_Point = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, kurir.getNamaKurir());
            stmt.setInt(2, kurir.getTotalSampah());
            stmt.setInt(3, kurir.getTotalPoint());
            stmt.setInt(4, kurir.getId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deleteKurir(int id) throws SQLException {
        String query = "DELETE FROM kurir WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
