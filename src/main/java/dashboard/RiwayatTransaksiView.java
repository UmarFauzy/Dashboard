package dashboard;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
        String[] columnNames = {
            "ID", "Tanggal", "Nama Pengguna", "Nama Kurir", "Nama Sampah", 
            "Kategori Sampah", "Total Sampah", "Total Point", "DropBox", "Daerah", "No Handphone"
        };
        Object[][] data = {
            {1, "2025-01-01", "John Doe", "Kurir A", "Sampah A", "Plastik", 10, 100, "DropBox A", "Jakarta", "081234567890"},
            {2, "2025-01-02", "Jane Smith", "Kurir B", "Sampah B", "Logam", 15, 150, "DropBox B", "Bandung", "081298765432"}
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Input form
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data Riwayat Transaksi"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Define fields
        String[] labels = {"ID:", "Tanggal:", "Nama Pengguna:", "Nama Kurir:", "Nama Sampah:", 
                           "Kategori Sampah:", "Total Sampah:", "Total Point:", "DropBox:", "Daerah:", "No Handphone:"};
        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            JTextField txtField = new JTextField();
            txtField.setPreferredSize(new Dimension(200, 30));
            fields[i] = txtField;

            gbc.gridx = (i % 2 == 0) ? 0 : 2; // Columns
            gbc.gridy = i / 2; // Rows
            inputPanel.add(lbl, gbc);

            gbc.gridx = gbc.gridx + 1;
            inputPanel.add(txtField, gbc);
        }

        // Buttons
        JButton btnCreate = new JButton("Tambah");
        JButton btnUpdate = new JButton("Ubah");
        JButton btnDelete = new JButton("Hapus");

        gbc.gridx = 0;
        gbc.gridy = labels.length / 2 + 1;
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
