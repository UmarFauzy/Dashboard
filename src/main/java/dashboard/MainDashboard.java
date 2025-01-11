package dashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainDashboard {

    private static Connection connection; // Field untuk koneksi database

    public static void main(String[] args) {
        // Inisialisasi koneksi database
        initializeDatabaseConnection();

        // Membuat frame utama
        JFrame frame = createMainFrame();

        // Membuat panel sidebar
        JPanel sidebar = createSidebar(frame);

        // Membuat panel konten utama
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Tambahkan sidebar dan panel konten ke frame utama
        frame.add(sidebar, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Action listener untuk tombol Pengguna
        JButton btnPengguna = (JButton) ((JPanel) sidebar.getComponent(1)).getComponent(0);
        btnPengguna.addActionListener(e -> {
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Koneksi database tidak tersedia!");
                return;
            }
            updateContentPanel(contentPanel, new UserView(connection));
        });

        // Action listener untuk tombol Kurir
        JButton btnKurir = (JButton) ((JPanel) sidebar.getComponent(1)).getComponent(1);
        btnKurir.addActionListener(e -> {
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Koneksi database tidak tersedia!");
                return;
            }
            updateContentPanel(contentPanel, new KurirView(connection));
        });

        // Action listener untuk tombol Daerah
        JButton btnDaerah = new JButton("Daerah");
        btnDaerah.addActionListener(e -> {
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Koneksi database tidak tersedia!");
                return;
            }
            updateContentPanel(contentPanel, new DaerahView(connection));
        });
        ((JPanel) sidebar.getComponent(1)).add(btnDaerah);

        // Action listener untuk tombol Dropbox
        JButton btnDropbox = new JButton("Dropbox");
        btnDropbox.addActionListener(e -> {
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Koneksi database tidak tersedia!");
                return;
            }
            updateContentPanel(contentPanel, new DropBoxView(connection));
        });
        ((JPanel) sidebar.getComponent(1)).add(btnDropbox);

        // Action listener untuk tombol Kategori Sampah
        JButton btnKategoriSampah = new JButton("Kategori Sampah");
        btnKategoriSampah.addActionListener(e -> {
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Koneksi database tidak tersedia!");
                return;
            }
            updateContentPanel(contentPanel, new KategoriSampahView(connection));
        });
        ((JPanel) sidebar.getComponent(1)).add(btnKategoriSampah);

        // Action listener untuk tombol Keseluruhan Sampah
        JButton btnKeseluruhanSampah = new JButton("Keseluruhan Sampah");
        btnKeseluruhanSampah.addActionListener(e -> {
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Koneksi database tidak tersedia!");
                return;
            }
            updateContentPanel(contentPanel, new KeseluruhanSampahView(connection));  // Pastikan view ini muncul
        });

        // Menghapus tombol yang sudah ada sebelum menambah tombol Keseluruhan Sampah
        JPanel buttonPanel = (JPanel) sidebar.getComponent(1);
        for (Component component : buttonPanel.getComponents()) {
            if (component instanceof JButton && ((JButton) component).getText().equals("Keseluruhan Sampah")) {
                buttonPanel.remove(component); // Menghapus tombol yang sudah ada
            }
        }

        // Menambahkan tombol Keseluruhan Sampah hanya satu kali
        buttonPanel.add(btnKeseluruhanSampah);

        // Action listener untuk tombol Riwayat Transaksi
        JButton btnRiwayatTransaksi = new JButton("Riwayat Transaksi");
        btnRiwayatTransaksi.addActionListener(e -> {
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Koneksi database tidak tersedia!");
                return;
            }
            updateContentPanel(contentPanel, new RiwayatTransaksiView(connection));
        });
        ((JPanel) sidebar.getComponent(1)).add(btnRiwayatTransaksi);
    }

    private static void initializeDatabaseConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubespp2", "root", "");
            System.out.println("Koneksi berhasil ke database!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal koneksi ke database: " + e.getMessage());
            System.exit(1); // Keluar dari program jika koneksi gagal
        }
    }

    private static JFrame createMainFrame() {
        JFrame frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 900);
        return frame;
    }

    private static JPanel createSidebar(JFrame frame) {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(Color.GREEN);
        sidebar.setPreferredSize(new Dimension(250, 0));

        JLabel title = new JLabel("Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        sidebar.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(10, 1, 5, 5)); // Mengatur jumlah baris agar sesuai dengan tombol yang ada
        buttonPanel.setBackground(Color.GREEN);

        JButton btnPengguna = new JButton("Pengguna");
        buttonPanel.add(btnPengguna);

        JButton btnKurir = new JButton("Kurir");
        buttonPanel.add(btnKurir);

//        JButton btnDaerah = new JButton("Daerah");
//        buttonPanel.add(btnDaerah);
//
//        JButton btnDropbox = new JButton("Dropbox");
//        buttonPanel.add(btnDropbox);
//
//        JButton btnKategoriSampah = new JButton("Kategori Sampah");
//        buttonPanel.add(btnKategoriSampah);
//        JButton btnRiwayatTransaksi = new JButton("Riwayat Transaksi");
//        buttonPanel.add(btnRiwayatTransaksi);
        sidebar.add(buttonPanel, BorderLayout.CENTER);

        return sidebar;
    }

    private static void updateContentPanel(JPanel contentPanel, JComponent newContent) {
        contentPanel.removeAll();
        contentPanel.add(newContent, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
