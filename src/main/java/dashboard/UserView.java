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

public class UserView extends JPanel {

    public UserView() {
        setLayout(new BorderLayout());

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter"));

        JLabel lblSearch = new JLabel("Search:");
        JTextField txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(200, 30)); // Adjust size
        JLabel lblSort = new JLabel("Sortir:");
        JComboBox<String> cbSort = new JComboBox<>(new String[]{"Top", "Bottom"});
        cbSort.setPreferredSize(new Dimension(120, 30)); // Adjust size

        filterPanel.add(lblSearch);
        filterPanel.add(txtSearch);
        filterPanel.add(lblSort);
        filterPanel.add(cbSort);

        // Table
        String[] columnNames = {"ID", "Nama Pengguna", "Daerah", "Total Sampah", "Total Point", "Nomor HP"};
        Object[][] data = {
            {1, "John Doe", "Jakarta", 150, 3000, "081234567890"},
            {2, "Jane Smith", "Bandung", 120, 2500, "081298765432"},
            {3, "Alice Brown", "Surabaya", 100, 2000, "081345678901"}
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Input form
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data Pengguna"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblID = new JLabel("ID:");
        JTextField txtID = new JTextField();
        txtID.setPreferredSize(new Dimension(300, 30)); // Adjust size

        JLabel lblNamaPengguna = new JLabel("Nama Pengguna:");
        JTextField txtNamaPengguna = new JTextField();
        txtNamaPengguna.setPreferredSize(new Dimension(300, 30)); // Adjust size

        JLabel lblDaerah = new JLabel("Daerah:");
        JTextField txtDaerah = new JTextField();
        txtDaerah.setPreferredSize(new Dimension(300, 30)); // Adjust size

        JLabel lblTotalSampah = new JLabel("Total Sampah:");
        JTextField txtTotalSampah = new JTextField();
        txtTotalSampah.setPreferredSize(new Dimension(300, 30)); // Adjust size

        JLabel lblTotalPoint = new JLabel("Total Point:");
        JTextField txtTotalPoint = new JTextField();
        txtTotalPoint.setPreferredSize(new Dimension(300, 30)); // Adjust size

        JLabel lblNoHp = new JLabel("Nomor HP:");
        JTextField txtNoHp = new JTextField();
        txtNoHp.setPreferredSize(new Dimension(300, 30)); // Adjust size

        JButton btnCreate = new JButton("Tambah");
        JButton btnUpdate = new JButton("Ubah");
        JButton btnDelete = new JButton("Hapus");
        btnCreate.setPreferredSize(new Dimension(100, 30)); // Adjust size
        btnUpdate.setPreferredSize(new Dimension(100, 30)); // Adjust size
        btnDelete.setPreferredSize(new Dimension(100, 30)); // Adjust size

        // Add input components
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(lblID, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtID, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lblNamaPengguna, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtNamaPengguna, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(lblDaerah, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtDaerah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(lblTotalSampah, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtTotalSampah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(lblTotalPoint, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtTotalPoint, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(lblNoHp, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtNoHp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
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
