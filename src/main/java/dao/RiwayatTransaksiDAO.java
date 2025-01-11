package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.RiwayatTransaksi;

public class RiwayatTransaksiDAO {

    private Connection connection;

    public RiwayatTransaksiDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void insertRiwayatTransaksi(RiwayatTransaksi transaksi) throws SQLException {
        String query = "INSERT INTO riwayat_transaksi (Tanggal, User_ID, Kurir_ID, Sampah_ID, Kategori_ID, "
                + "Total_Sampah, Total_Point, DropBox_ID, Daerah_ID, No_Handphone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            if (transaksi.getTanggal() != null) {
                stmt.setDate(1, new java.sql.Date(transaksi.getTanggal().getTime())); // Tanggal
            } else {
                // Tangani jika tanggal tidak valid
                throw new SQLException("Tanggal transaksi tidak valid");
            }

            stmt.setInt(2, transaksi.getUserId()); // User_ID
            stmt.setInt(3, transaksi.getKurirId()); // Kurir_ID
            stmt.setInt(4, transaksi.getSampahId()); // Sampah_ID
            stmt.setInt(5, transaksi.getKategoriId()); // Kategori_ID
            stmt.setDouble(6, transaksi.getTotalSampah()); // Total_Sampah
            stmt.setInt(7, transaksi.getTotalPoint()); // Total_Point
            stmt.setInt(8, transaksi.getDropBoxId()); // DropBox_ID
            stmt.setInt(9, transaksi.getDaerahId()); // Daerah_ID
            stmt.setString(10, transaksi.getNoHandphone()); // No_Handphone

            stmt.executeUpdate();
        }
    }

    // READ
    public List<RiwayatTransaksi> getAllRiwayatTransaksi() throws SQLException {
        String query = "SELECT * FROM riwayat_transaksi";
        List<RiwayatTransaksi> transaksiList = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                transaksiList.add(new RiwayatTransaksi(
                        rs.getInt("ID"),
                        rs.getDate("Tanggal"),
                        rs.getInt("User_ID"),
                        rs.getInt("Kurir_ID"),
                        rs.getInt("Sampah_ID"),
                        rs.getInt("Kategori_ID"),
                        rs.getDouble("Total_Sampah"),
                        rs.getInt("Total_Point"),
                        rs.getInt("DropBox_ID"),
                        rs.getInt("Daerah_ID"),
                        rs.getString("No_Handphone")
                ));
            }
        }

        return transaksiList;
    }

    // UPDATE
    public void updateRiwayatTransaksi(RiwayatTransaksi transaksi) throws SQLException {
        String query = "UPDATE riwayat_transaksi SET Tanggal = ?, User_ID = ?, Kurir_ID = ?, Sampah_ID = ?, "
                + "Kategori_ID = ?, Total_Sampah = ?, Total_Point = ?, DropBox_ID = ?, Daerah_ID = ?, No_Handphone = ? WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            if (transaksi.getTanggal() != null) {
                stmt.setDate(1, new java.sql.Date(transaksi.getTanggal().getTime())); // Tanggal
            } else {
                // Tangani jika tanggal tidak valid
                throw new SQLException("Tanggal transaksi tidak valid");
            }

            stmt.setInt(2, transaksi.getUserId()); // User_ID
            stmt.setInt(3, transaksi.getKurirId()); // Kurir_ID
            stmt.setInt(4, transaksi.getSampahId()); // Sampah_ID
            stmt.setInt(5, transaksi.getKategoriId()); // Kategori_ID
            stmt.setDouble(6, transaksi.getTotalSampah()); // Total_Sampah
            stmt.setInt(7, transaksi.getTotalPoint()); // Total_Point
            stmt.setInt(8, transaksi.getDropBoxId()); // DropBox_ID
            stmt.setInt(9, transaksi.getDaerahId()); // Daerah_ID
            stmt.setString(10, transaksi.getNoHandphone()); // No_Handphone
            stmt.setInt(11, transaksi.getId()); // ID untuk WHERE

            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deleteRiwayatTransaksi(int id) throws SQLException {
        String query = "DELETE FROM riwayat_transaksi WHERE ID = ?";
        System.out.println("Eksekusi Delete untuk ID: " + id); // Debugging untuk memverifikasi eksekusi query

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Delete berhasil untuk ID: " + id);
            } else {
                System.out.println("Delete gagal, ID tidak ditemukan: " + id);
            }
        }
    }
}
