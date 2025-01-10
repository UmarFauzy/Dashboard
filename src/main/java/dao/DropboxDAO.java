package dao;

import model.Dropbox;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DropboxDAO {
    private Connection connection;

    public DropboxDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void insertDropbox(Dropbox dropbox) throws SQLException {
        String query = "INSERT INTO dropbox (Nama_DropBox, Daerah_ID, Total_Sampah, Total_Point) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, dropbox.getNamaDropBox());
            stmt.setInt(2, dropbox.getDaerahID());
            stmt.setDouble(3, dropbox.getTotalSampah());
            stmt.setInt(4, dropbox.getTotalPoint());
            stmt.executeUpdate();
        }
    }

    // READ
    public List<Dropbox> getAllDropbox() throws SQLException {
        List<Dropbox> dropboxList = new ArrayList<>();
        String query = "SELECT * FROM dropbox";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                dropboxList.add(new Dropbox(
                        rs.getInt("ID"),
                        rs.getString("Nama_DropBox"),
                        rs.getInt("Daerah_ID"),
                        rs.getDouble("Total_Sampah"),
                        rs.getInt("Total_Point")
                ));
            }
        }
        return dropboxList;
    }

    // UPDATE
    public void updateDropbox(Dropbox dropbox) throws SQLException {
        String query = "UPDATE dropbox SET Nama_DropBox = ?, Daerah_ID = ?, Total_Sampah = ?, Total_Point = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, dropbox.getNamaDropBox());
            stmt.setInt(2, dropbox.getDaerahID());
            stmt.setDouble(3, dropbox.getTotalSampah());
            stmt.setInt(4, dropbox.getTotalPoint());
            stmt.setInt(5, dropbox.getId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deleteDropbox(int id) throws SQLException {
        String query = "DELETE FROM dropbox WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
