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

public class DaerahView extends JPanel {

    public DaerahView() {
        setLayout(new BorderLayout());

        // Panel untuk filter
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter"));

        JLabel lblSearch = new JLabel("Cari:");
        JTextField txtSearch = new JTextField(15);
        JLabel lblSort = new JLabel("Sortir:");
        JComboBox<String> cbSort = new JComboBox<>(new String[]{"Top", "Bottom"});

        filterPanel.add(lblSearch);
        filterPanel.add(txtSearch);
        filterPanel.add(lblSort);
        filterPanel.add(cbSort);

        // Tabel untuk data Daerah
        String[] columnNames = {"ID", "Daerah", "Total Sampah", "Total Poin"};
        Object[][] data = {
            {1, "Jakarta", 500, 1000},
            {2, "Bandung", 300, 750},
            {3, "Surabaya", 450, 900}
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);

 // Input form
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data Daerah"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblID = new JLabel("ID:");
        JTextField txtID = new JTextField();
        txtID.setPreferredSize(new Dimension(300, 30)); // Adjust size

        JLabel lblDaerah = new JLabel("Daerah:");
        JTextField txtDaerah = new JTextField();
        txtDaerah.setPreferredSize(new Dimension(300, 30)); // Adjust size

        JLabel lblTotalSampah = new JLabel("Total Sampah:");
        JTextField txtTotalSampah = new JTextField();
        txtTotalSampah.setPreferredSize(new Dimension(300, 30)); // Adjust size

        JLabel lblTotalPoint = new JLabel("Total Point:");
        JTextField txtTotalPoint = new JTextField();
        txtTotalPoint.setPreferredSize(new Dimension(300, 30)); // Adjust size

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
        inputPanel.add(lblDaerah, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtDaerah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(lblTotalSampah, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtTotalSampah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(lblTotalPoint, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtTotalPoint, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
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
