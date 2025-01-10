package dashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainDashboard {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 900);

        // Create the sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(Color.GREEN);
        sidebar.setPreferredSize(new Dimension(250, 0));

        JLabel title = new JLabel("Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.BLACK);
        sidebar.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1, 5, 5));
        buttonPanel.setBackground(Color.GREEN);

        // Create buttons with icons for the sidebar
        JButton btnKategoriSampah = new JButton("Kategori Sampah");
        JButton btnKeseluruhanSampah = new JButton("Keseluruhan Sampah");
        JButton btnDropBox = new JButton("DropBox");
        JButton btnDaerah = new JButton("Daerah");
        JButton btnRiwayatTransaksi = new JButton("Riwayat Transaksi");
        JButton btnKurir = new JButton("Kurir");
        JButton btnPengguna = new JButton("Pengguna");

        // Add buttons to the button panel
        buttonPanel.add(btnKategoriSampah);
        buttonPanel.add(btnKeseluruhanSampah);
        buttonPanel.add(btnDropBox);
        buttonPanel.add(btnDaerah);
        buttonPanel.add(btnRiwayatTransaksi);
        buttonPanel.add(btnKurir);
        buttonPanel.add(btnPengguna);

        sidebar.add(buttonPanel, BorderLayout.CENTER);

        // Create the main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Add sidebar and content panel to the frame
        frame.add(sidebar, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        // Show frame
        frame.setVisible(true);

        // Action listener for buttons
        // btnKategoriSampah.addActionListener(e -> updateContentPanel(contentPanel, new KategoriSampahView()));
        // btnKeseluruhanSampah.addActionListener(e -> updateContentPanel(contentPanel, new KeseluruhanSampahView()));
        // btnDropBox.addActionListener(e -> updateContentPanel(contentPanel, new DropBoxView()));
        // btnDaerah.addActionListener(e -> updateContentPanel(contentPanel, new DaerahView()));
        // btnRiwayatTransaksi.addActionListener(e -> updateContentPanel(contentPanel, new RiwayatTransaksiView()));
        // btnKurir.addActionListener(e -> updateContentPanel(contentPanel, new KurirView()));
        // btnPengguna.addActionListener(e -> updateContentPanel(contentPanel, new UserView()));
    }

    private static JButton createSidebarButton(String text, String iconPath) {
        // Load icon if available
        ImageIcon icon = null;
        java.io.File iconFile = new java.io.File(iconPath);
        if (iconFile.exists()) {
            icon = new ImageIcon(iconPath);
        }

        JButton button = new JButton(text, icon);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setBackground(Color.WHITE);
        button.setPreferredSize(new Dimension(130, 40)); // Set button size
        return button;
    }

    private static void updateContentPanel(JPanel contentPanel, JComponent newContent) {
        contentPanel.removeAll();
        contentPanel.add(newContent, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}