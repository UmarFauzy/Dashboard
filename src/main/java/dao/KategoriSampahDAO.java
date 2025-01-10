package dao;

import model.KategoriSampah;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategoriSampahDAO {

    private Connection connection;

    public KategoriSampahDAO(Connection connection) {
        this.connection = connection;
    }

        // CREATE
        public void insertKategoriSampah(KategoriSampah kategoriSampah) throws SQLException {
            String query = "INSERT INTO kategori_sampah (Nama_Kategori) VALUES (?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, kategoriSampah.getNamaKategori());
                stmt.executeUpdate();
            }
        }


    // Method untuk mengambil KategoriSampah berdasarkan 'No'
    public KategoriSampah getKategoriSampahByNo(int no) {
        KategoriSampah kategoriSampah = null;
        String query = "SELECT * FROM kategori_sampah WHERE No = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, no);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String namaKategori = rs.getString("Nama_Kategori");
                kategoriSampah = new KategoriSampah(no, namaKategori);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kategoriSampah;
    }

    // Method untuk mengambil semua KategoriSampah
    public List<KategoriSampah> getAllKategoriSampah() throws SQLException {
        List<KategoriSampah> kategoriSampahList = new ArrayList<>();
        String query = "SELECT * FROM kategori_sampah";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                kategoriSampahList.add(new KategoriSampah(
                        rs.getInt("No"),
                        rs.getString("Nama_Kategori")
                ));
            }
        }
        return kategoriSampahList;
    }

    // Method untuk memperbarui KategoriSampah
    public void updateKategoriSampah(KategoriSampah kategoriSampah) throws SQLException {
        String query = "UPDATE kategori_sampah SET Nama_Kategori = ? WHERE No = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, kategoriSampah.getNamaKategori());
            stmt.setInt(2, kategoriSampah.getNo());
            stmt.executeUpdate();
        }
    }

    // Method untuk menghapus KategoriSampah berdasarkan No
    public void deleteKategoriSampah(int no) throws SQLException {
        String query = "DELETE FROM kategori_sampah WHERE No = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, no);
            stmt.executeUpdate();
        }
    }

    // Method untuk menambah KategoriSampah baru
    public void createKategoriSampah(KategoriSampah kategoriSampah) throws SQLException {
        String query = "INSERT INTO kategori_sampah (Nama_Kategori) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, kategoriSampah.getNamaKategori());
            stmt.executeUpdate();
        }
    }
}
