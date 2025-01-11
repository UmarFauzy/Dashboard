package dashboard;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;

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
        JButton btnDaerah = (JButton) ((JPanel) sidebar.getComponent(1)).getComponent(2);
        btnDaerah.addActionListener(e -> {
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Koneksi database tidak tersedia!");
                return;
            }
            updateContentPanel(contentPanel, new DaerahView(connection));
        });

        // Action listener untuk tombol Dropbox
        JButton btnDropbox = (JButton) ((JPanel) sidebar.getComponent(1)).getComponent(3);
        btnDropbox.addActionListener(e -> {
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Koneksi database tidak tersedia!");
                return;
            }
            updateContentPanel(contentPanel, new DropBoxView(connection));
        });

        // Action listener untuk tombol Kategori Sampah
        JButton btnKategoriSampah = (JButton) ((JPanel) sidebar.getComponent(1)).getComponent(4);
        btnKategoriSampah.addActionListener(e -> {
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Koneksi database tidak tersedia!");
                return;
            }
            updateContentPanel(contentPanel, new KategoriSampahView(connection));
        });



        // Action listener untuk tombol Keseluruhan Sampah
        JButton btnKeseluruhanSampah =(JButton) ((JPanel) sidebar.getComponent(1)).getComponent(5);
        btnKeseluruhanSampah.addActionListener(e -> {
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Koneksi database tidak tersedia!");
                return;
            }
            updateContentPanel(contentPanel, new KeseluruhanSampahView(connection));
        });
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
        frame.setSize(1200, 800);
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

        JButton btnDaerah = new JButton("Daerah");
        buttonPanel.add(btnDaerah);

        JButton btnDropBox = new JButton("DropBox");
        buttonPanel.add(btnDropBox);

        JButton btnKategoriSampah = new JButton("Kategori Sampah");
        buttonPanel.add(btnKategoriSampah);

        JButton btnKeseluruhanSampah = new JButton("Keseluruhan Sampah");
        buttonPanel.add(btnKeseluruhanSampah);

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
