package dashboard;

import dao.DaerahDAO;
import model.Daerah;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.List;

public class DaerahView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtDaerah, txtTotalSampah, txtTotalPoint, txtSearch;
    private DaerahDAO daerahDAO;

    public DaerahView(Connection connection) {
        this.daerahDAO = new DaerahDAO(connection); // Inisialisasi DAO
        setLayout(new BorderLayout());

        // Panel untuk filter
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter"));

        JLabel lblSearch = new JLabel("Cari:");
        txtSearch = new JTextField(20); // Inisialisasi search field
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
        txtDaerah.setPreferredSize(new Dimension(300, 30));

        JLabel lblTotalSampah = new JLabel("Total Sampah:");
        txtTotalSampah = new JTextField();
        txtTotalSampah.setPreferredSize(new Dimension(300, 30));

        JLabel lblTotalPoint = new JLabel("Total Point:");
        txtTotalPoint = new JTextField();
        txtTotalPoint.setPreferredSize(new Dimension(300, 30));

        JButton btnCreate = new JButton("Tambah");
        JButton btnUpdate = new JButton("Ubah");
        JButton btnDelete = new JButton("Hapus");
        btnCreate.setPreferredSize(new Dimension(100, 30));
        btnUpdate.setPreferredSize(new Dimension(100, 30));
        btnDelete.setPreferredSize(new Dimension(100, 30));

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
                txtDaerah.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtTotalSampah.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtTotalPoint.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        });

        // Event Listener for CRUD buttons
        btnCreate.addActionListener(e -> addDaerah());
        btnUpdate.addActionListener(e -> updateDaerah());
        btnDelete.addActionListener(e -> deleteDaerah());

        // Event Listener for Search Field
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtSearch.getText().trim();
                searchDaerah(keyword);
            }
        });

        // Load data into table
        loadDaerahData();
    }

    private void loadDaerahData() {
        try {
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

    private void addDaerah() {
        try {
            String namaDaerah = txtDaerah.getText();
            double totalSampah = Double.parseDouble(txtTotalSampah.getText());
            int totalPoint = Integer.parseInt(txtTotalPoint.getText());

            Daerah newDaerah = new Daerah(0, namaDaerah, totalSampah, totalPoint);
            daerahDAO.insertDaerah(newDaerah);

            JOptionPane.showMessageDialog(this, "Daerah berhasil ditambahkan!");
            loadDaerahData();
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding daerah: " + e.getMessage());
        }
    }

    private void updateDaerah() {
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
            daerahDAO.updateDaerah(updatedDaerah);

            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
            loadDaerahData();
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating daerah: " + e.getMessage());
        }
    }

    private void deleteDaerah() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
                return;
            }

            int id = (int) tableModel.getValueAt(selectedRow, 0);
            daerahDAO.deleteDaerah(id);

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            loadDaerahData();
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting daerah: " + e.getMessage());
        }
    }

    private void searchDaerah(String keyword) {
        try {
            List<Daerah> daerahList = daerahDAO.searchDaerahByName(keyword);
            tableModel.setRowCount(0); // Clear table data
            for (Daerah daerah : daerahList) {
                tableModel.addRow(new Object[]{
                        daerah.getId(),
                        daerah.getNamaDaerah(),
                        daerah.getTotalSampah(),
                        daerah.getTotalPoint()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
        }
    }

    private void clearInputFields() {
        txtDaerah.setText("");
        txtTotalSampah.setText("");
        txtTotalPoint.setText("");
    }
}
