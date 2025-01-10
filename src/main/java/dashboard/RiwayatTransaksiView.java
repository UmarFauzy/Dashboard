package dashboard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class RiwayatTransaksiView extends JPanel {

    public RiwayatTransaksiView() {
        setLayout(new BorderLayout());

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter"));

        JLabel lblSearch = new JLabel("Cari:");
        JTextField txtSearch = new JTextField(15);
        JLabel lblSort = new JLabel("Sortir:");
        JComboBox<String> cbSort = new JComboBox<>(new String[]{"Terbaru", "Terlama"});

        filterPanel.add(lblSearch);
        filterPanel.add(txtSearch);
        filterPanel.add(lblSort);
        filterPanel.add(cbSort);

        // Table
        String[] columnNames = {"ID", "Tanggal", "Nama Pengguna", "Nama Kurir", "Total Sampah", "Total Point", "DropBox", "Daerah", "No Handphone"};
        Object[][] data = {
            {1, "2025-01-01", "John Doe", "Kurir A", 10, 100, "DropBox A", "Jakarta", "081234567890"},
            {2, "2025-01-02", "Jane Smith", "Kurir B", 15, 150, "DropBox B", "Bandung", "081298765432"}
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Input form
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data Riwayat Transaksi"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblID = new JLabel("ID:");
        JTextField txtID = new JTextField();
        txtID.setPreferredSize(new Dimension(200, 30));

        JLabel lblTanggal = new JLabel("Tanggal:");
        JTextField txtTanggal = new JTextField();
        txtTanggal.setPreferredSize(new Dimension(200, 30));

        JLabel lblNamaPengguna = new JLabel("Nama Pengguna:");
        JTextField txtNamaPengguna = new JTextField();
        txtNamaPengguna.setPreferredSize(new Dimension(200, 30));

        JLabel lblNamaKurir = new JLabel("Nama Kurir:");
        JTextField txtNamaKurir = new JTextField();
        txtNamaKurir.setPreferredSize(new Dimension(200, 30));

        JLabel lblTotalSampah = new JLabel("Total Sampah:");
        JTextField txtTotalSampah = new JTextField();
        txtTotalSampah.setPreferredSize(new Dimension(200, 30));

        JLabel lblTotalPoint = new JLabel("Total Point:");
        JTextField txtTotalPoint = new JTextField();
        txtTotalPoint.setPreferredSize(new Dimension(200, 30));

        JLabel lblDropBox = new JLabel("DropBox:");
        JTextField txtDropBox = new JTextField();
        txtDropBox.setPreferredSize(new Dimension(200, 30));

        JLabel lblDaerah = new JLabel("Daerah:");
        JTextField txtDaerah = new JTextField();
        txtDaerah.setPreferredSize(new Dimension(200, 30));

        JLabel lblNoHandphone = new JLabel("No Handphone:");
        JTextField txtNoHandphone = new JTextField();
        txtNoHandphone.setPreferredSize(new Dimension(200, 30));

        JButton btnCreate = new JButton("Tambah");
        JButton btnUpdate = new JButton("Ubah");
        JButton btnDelete = new JButton("Hapus");
        btnCreate.setPreferredSize(new Dimension(100, 30));
        btnUpdate.setPreferredSize(new Dimension(100, 30));
        btnDelete.setPreferredSize(new Dimension(100, 30));

        // Add input components in two columns
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(lblID, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtID, gbc);

        gbc.gridx = 2;
        inputPanel.add(lblTanggal, gbc);
        gbc.gridx = 3;
        inputPanel.add(txtTanggal, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lblNamaPengguna, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtNamaPengguna, gbc);

        gbc.gridx = 2;
        inputPanel.add(lblNamaKurir, gbc);
        gbc.gridx = 3;
        inputPanel.add(txtNamaKurir, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(lblTotalSampah, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtTotalSampah, gbc);

        gbc.gridx = 2;
        inputPanel.add(lblTotalPoint, gbc);
        gbc.gridx = 3;
        inputPanel.add(txtTotalPoint, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(lblDropBox, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtDropBox, gbc);

        gbc.gridx = 2;
        inputPanel.add(lblDaerah, gbc);
        gbc.gridx = 3;
        inputPanel.add(txtDaerah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(lblNoHandphone, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtNoHandphone, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(btnCreate, gbc);
        gbc.gridx = 1;
        inputPanel.add(btnUpdate, gbc);
        gbc.gridx = 2;
        inputPanel.add(btnDelete, gbc);

        // Add components to main panel
        add(filterPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }
}
