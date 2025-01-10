package dao;

import model.KeseluruhanSampah;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KeseluruhanSampahDAO {
    private Connection connection;

    public KeseluruhanSampahDAO(Connection connection) {
        this.connection = connection;
    }

    public List<KeseluruhanSampah> getAllKeseluruhanSampah() throws SQLException {
        String query = "SELECT * FROM keseluruhan_sampah";
        List<KeseluruhanSampah> sampahList = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int no = rs.getInt("No");
                String jenisSampah = rs.getString("Jenis_Sampah");
                int kategoriSampahId = rs.getInt("Kategori_Sampah");
                double totalSampah = rs.getDouble("Total_Sampah");

                sampahList.add(new KeseluruhanSampah(no, jenisSampah, kategoriSampahId, totalSampah));
            }
        }
        
        return sampahList;
    }

    public void insertKeseluruhanSampah(KeseluruhanSampah sampah) throws SQLException {
        String query = "INSERT INTO keseluruhan_sampah (Jenis_Sampah, Kategori_Sampah, Total_Sampah) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, sampah.getJenisSampah());
            stmt.setInt(2, sampah.getKategoriSampahId());
            stmt.setDouble(3, sampah.getTotalSampah());
            stmt.executeUpdate();
        }
    }

    public void updateKeseluruhanSampah(KeseluruhanSampah sampah) throws SQLException {
        String query = "UPDATE keseluruhan_sampah SET Jenis_Sampah = ?, Kategori_Sampah = ?, Total_Sampah = ? WHERE No = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, sampah.getJenisSampah());
            stmt.setInt(2, sampah.getKategoriSampahId());
            stmt.setDouble(3, sampah.getTotalSampah());
            stmt.setInt(4, sampah.getNo());
            stmt.executeUpdate();
        }
    }

    public void deleteKeseluruhanSampah(int no) throws SQLException {
        String query = "DELETE FROM keseluruhan_sampah WHERE No = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, no);
            stmt.executeUpdate();
        }
    }
}
