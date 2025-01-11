package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Create_DB {
    // URL koneksi database
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root"; // Ganti dengan username MySQL Anda
    private static final String PASSWORD = ""; // Ganti dengan password MySQL Anda
    private static final String DATABASE_NAME = "tubespp2"; // Nama database yang akan dibuat

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Memuat driver JDBC MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Membuat koneksi ke server MySQL
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Koneksi berhasil ke MySQL!");

            // Membuat database jika belum ada
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
            connection.createStatement().executeUpdate(createDatabaseSQL);
            System.out.println("Database '" + DATABASE_NAME + "' berhasil dibuat!");

        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC tidak ditemukan: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("Koneksi ditutup.");
                }
            } catch (SQLException e) {
                System.out.println("Gagal menutup koneksi: " + e.getMessage());
            }
        }
    }
}
