package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Daerah;
import model.User;

public class UserDAO {

    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void insertUser(User user) throws SQLException {
        String query = "INSERT INTO user (Nama_Pengguna, Daerah_ID, Total_Sampah, Total_Point, Nomor_HP) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getNamaPengguna());
            stmt.setInt(2, user.getDaerahId());
            stmt.setDouble(3, user.getTotalSampah());
            stmt.setInt(4, user.getTotalPoint());
            stmt.setString(5, user.getNomorHp());
            stmt.executeUpdate();
        }
    }

    // READ USERS WITH DAERAH
    public List<User> getAllUsersWithDaerah() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT user.ID, user.Nama_Pengguna, daerah.Nama_Daerah, user.Total_Sampah, user.Total_Point, user.Nomor_HP "
                + "FROM user "
                + "JOIN daerah ON user.Daerah_ID = daerah.ID";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("ID"),
                        rs.getString("Nama_Pengguna"),
                        rs.getString("Nama_Daerah"),
                        rs.getDouble("Total_Sampah"),
                        rs.getInt("Total_Point"),
                        rs.getString("Nomor_HP")
                ));
            }
        }
        return users;
    }

    // READ DAFTAR DAERAH
    public List<Daerah> getDaerahList() throws SQLException {
        List<Daerah> daerahList = new ArrayList<>();
        String query = "SELECT * FROM daerah";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                // Menambahkan objek Daerah ke dalam list dengan informasi lengkap dari database
                daerahList.add(new Daerah(
                        rs.getInt("ID"),
                        rs.getString("Nama_Daerah"),
                        rs.getDouble("Total_Sampah"), // Mengambil Total_Sampah yang baru
                        rs.getInt("Total_Point") // Mengambil Total_Point yang baru
                ));
            }
        }
        return daerahList;
    }

    public List<String> getAllNoHandphone() throws SQLException {
        List<String> noHandphoneList = new ArrayList<>();
        String query = "SELECT Nomor_HP FROM user";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                noHandphoneList.add(rs.getString("Nomor_HP"));
            }
        }
        return noHandphoneList;
    }

    // UPDATE
    public void updateUser(User user) throws SQLException {
        String query = "UPDATE user SET Nama_Pengguna = ?, Daerah_ID = ?, Total_Sampah = ?, Total_Point = ?, Nomor_HP = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getNamaPengguna());
            stmt.setInt(2, user.getDaerahId());
            stmt.setDouble(3, user.getTotalSampah());
            stmt.setInt(4, user.getTotalPoint());
            stmt.setString(5, user.getNomorHp());
            stmt.setInt(6, user.getId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deleteUser(int id) throws SQLException {
        String query = "DELETE FROM user WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
