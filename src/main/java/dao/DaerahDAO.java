package dao;

import model.Daerah;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaerahDAO {
    private Connection connection;

    // Constructor
    public DaerahDAO(Connection connection) {
        this.connection = connection;
    }

    // SEARCH
    public List<Daerah> searchDaerahByName(String keyword) throws SQLException {
        List<Daerah> daerahList = new ArrayList<>();
        String query = "SELECT ID AS id, Nama_Daerah AS namaDaerah, Total_Sampah AS totalSampah, Total_Point AS totalPoint " +
                       "FROM daerah " +
                       "WHERE Nama_Daerah LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    daerahList.add(new Daerah(
                            rs.getInt("id"),
                            rs.getString("namaDaerah"),
                            rs.getDouble("totalSampah"),
                            rs.getInt("totalPoint")
                    ));
                }
            }
        }
        return daerahList;
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
        String query = "SELECT ID AS id, Nama_Daerah AS namaDaerah, Total_Sampah AS totalSampah, Total_Point AS totalPoint FROM daerah";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                daerahList.add(new Daerah(
                        rs.getInt("id"),
                        rs.getString("namaDaerah"),
                        rs.getDouble("totalSampah"),
                        rs.getInt("totalPoint")
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
