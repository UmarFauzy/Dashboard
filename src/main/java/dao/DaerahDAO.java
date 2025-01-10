package dao;

import model.Daerah;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaerahDAO {
    private Connection connection;

    public DaerahDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void insertDaerah(Daerah daerah) throws SQLException {
        String query = "INSERT INTO daerah (Nama_Daerah, Total_Sampah, Total_Point) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, daerah.getNamaDaerah());
            stmt.setDouble(2, daerah.getTotalSampah());
            stmt.setInt(3, daerah.getTotalPoint());
            stmt.executeUpdate();
        }
    }

    // READ
    public List<Daerah> getAllDaerah() throws SQLException {
        List<Daerah> daerahList = new ArrayList<>();
        String query = "SELECT * FROM daerah";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                daerahList.add(new Daerah(
                        rs.getInt("ID"),
                        rs.getString("Nama_Daerah"),
                        rs.getDouble("Total_Sampah"),
                        rs.getInt("Total_Point")
                ));
            }
        }
        return daerahList;
    }

    // UPDATE
    public void updateDaerah(Daerah daerah) throws SQLException {
        String query = "UPDATE daerah SET Nama_Daerah = ?, Total_Sampah = ?, Total_Point = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, daerah.getNamaDaerah());
            stmt.setDouble(2, daerah.getTotalSampah());
            stmt.setInt(3, daerah.getTotalPoint());
            stmt.setInt(4, daerah.getId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deleteDaerah(int id) throws SQLException {
        String query = "DELETE FROM daerah WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
