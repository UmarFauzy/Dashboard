package dashboard;

import dao.DaerahDAO;
import model.Daerah;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class DaerahView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtDaerah, txtTotalSampah, txtTotalPoint;

    public DaerahView(Connection connection) {
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
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Input form tanpa ID
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data Daerah"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblDaerah = new JLabel("Daerah:");
        txtDaerah = new JTextField();
        txtDaerah.setPreferredSize(new Dimension(300, 30)); // Adjust size

        JLabel lblTotalSampah = new JLabel("Total Sampah:");
        txtTotalSampah = new JTextField();
        txtTotalSampah.setPreferredSize(new Dimension(300, 30)); // Adjust size

        JLabel lblTotalPoint = new JLabel("Total Point:");
        txtTotalPoint = new JTextField();
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
        inputPanel.add(lblDaerah, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtDaerah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lblTotalSampah, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtTotalSampah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(lblTotalPoint, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtTotalPoint, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(btnCreate, gbc);
        gbc.gridx = 1;
        inputPanel.add(btnUpdate, gbc);
        gbc.gridx = 2;
        inputPanel.add(btnDelete, gbc);

        // Add components to main panel
        add(filterPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Event Listener for table row selection
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Fill the input fields with selected row data
                txtDaerah.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtTotalSampah.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtTotalPoint.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        });

        // Event Listener for CRUD buttons
        btnCreate.addActionListener(e -> addDaerah(connection));
        btnUpdate.addActionListener(e -> updateDaerah(connection));
        btnDelete.addActionListener(e -> deleteDaerah(connection));

        // Load data into table
        loadDaerahData(connection);
    }

    private void loadDaerahData(Connection connection) {
        try {
            DaerahDAO daerahDAO = new DaerahDAO(connection);
            List<Daerah> daerahList = daerahDAO.getAllDaerah();

            tableModel.setRowCount(0); // Clear previous data in table
            for (Daerah daerah : daerahList) {
                tableModel.addRow(new Object[]{
                        daerah.getId(),
                        daerah.getNamaDaerah(),
                        daerah.getTotalSampah(),
                        daerah.getTotalPoint()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void addDaerah(Connection connection) {
        try {
            String namaDaerah = txtDaerah.getText();
            double totalSampah = Double.parseDouble(txtTotalSampah.getText());
            int totalPoint = Integer.parseInt(txtTotalPoint.getText());

            Daerah newDaerah = new Daerah(0, namaDaerah, totalSampah, totalPoint);
            DaerahDAO daerahDAO = new DaerahDAO(connection);
            daerahDAO.insertDaerah(newDaerah);

            JOptionPane.showMessageDialog(this, "Daerah berhasil ditambahkan!");
            loadDaerahData(connection);
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding daerah: " + e.getMessage());
        }
    }

    private void updateDaerah(Connection connection) {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin diubah!");
                return;
            }

            int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            String namaDaerah = txtDaerah.getText();
            double totalSampah = Double.parseDouble(txtTotalSampah.getText());
            int totalPoint = Integer.parseInt(txtTotalPoint.getText());

            Daerah updatedDaerah = new Daerah(id, namaDaerah, totalSampah, totalPoint);
            DaerahDAO daerahDAO = new DaerahDAO(connection);
            daerahDAO.updateDaerah(updatedDaerah);

            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
            loadDaerahData(connection);
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating daerah: " + e.getMessage());
        }
    }

    private void deleteDaerah(Connection connection) {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
                return;
            }

            int id = (int) tableModel.getValueAt(selectedRow, 0);
            DaerahDAO daerahDAO = new DaerahDAO(connection);
            daerahDAO.deleteDaerah(id);

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            loadDaerahData(connection);
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting daerah: " + e.getMessage());
        }
    }

    // Kosongkan kolom input
    private void clearInputFields() {
        txtDaerah.setText("");
        txtTotalSampah.setText("");
        txtTotalPoint.setText("");
    }
}
