package dashboard;

import dao.DropboxDAO;
import model.Dropbox;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.List;

public class DropBoxView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNamaDropbox, txtLokasiDropbox, txtTotalSampah, txtTotalPoint, txtSearch;
    private DropboxDAO dropboxDAO;

    public DropBoxView(Connection connection) {
        // Pastikan koneksi valid
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }

        this.dropboxDAO = new DropboxDAO(connection); // Inisialisasi DAO
        setLayout(new BorderLayout());

        // Panel untuk filter
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter"));

        JLabel lblSearch = new JLabel("Cari:");
        txtSearch = new JTextField(20);
        filterPanel.add(lblSearch);
        filterPanel.add(txtSearch);

        // Tabel untuk data Dropbox
        String[] columnNames = {"ID", "Nama Dropbox", "Daerah ID", "Total Sampah", "Total Point"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Input form
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data Dropbox"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNamaDropbox = new JLabel("Nama Dropbox:");
        txtNamaDropbox = new JTextField();
        txtNamaDropbox.setPreferredSize(new Dimension(300, 30));

        JLabel lblLokasiDropbox = new JLabel("Daerah ID:");
        txtLokasiDropbox = new JTextField();
        txtLokasiDropbox.setPreferredSize(new Dimension(300, 30));

        JLabel lblTotalSampah = new JLabel("Total Sampah:");
        txtTotalSampah = new JTextField();
        txtTotalSampah.setPreferredSize(new Dimension(300, 30));

        JLabel lblTotalPoint = new JLabel("Total Point:");
        txtTotalPoint = new JTextField();
        txtTotalPoint.setPreferredSize(new Dimension(300, 30));

        JButton btnCreate = new JButton("Tambah");
        JButton btnUpdate = new JButton("Ubah");
        JButton btnDelete = new JButton("Hapus");

        // Add input components
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(lblNamaDropbox, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtNamaDropbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lblLokasiDropbox, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtLokasiDropbox, gbc);

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

        // Event Listener for table row selection
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                txtNamaDropbox.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtLokasiDropbox.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtTotalSampah.setText(tableModel.getValueAt(selectedRow, 3).toString());
                txtTotalPoint.setText(tableModel.getValueAt(selectedRow, 4).toString());
            }
        });

        // Event Listener for CRUD buttons
        btnCreate.addActionListener(e -> addDropbox());
        btnUpdate.addActionListener(e -> updateDropbox());
        btnDelete.addActionListener(e -> deleteDropbox());

        // Event Listener for Search Field
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtSearch.getText().trim();
                searchDropbox(keyword);
            }
        });

        // Load data into table
        loadDropboxData();
    }

    private void loadDropboxData() {
        try {
            List<Dropbox> dropboxList = dropboxDAO.getAllDropbox();
            tableModel.setRowCount(0);
            for (Dropbox dropbox : dropboxList) {
                tableModel.addRow(new Object[]{
                        dropbox.getId(),
                        dropbox.getNamaDropBox(),
                        dropbox.getDaerahID(),
                        dropbox.getTotalSampah(),
                        dropbox.getTotalPoint()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void addDropbox() {
        try {
            String namaDropbox = txtNamaDropbox.getText();
            int daerahID = Integer.parseInt(txtLokasiDropbox.getText());
            double totalSampah = Double.parseDouble(txtTotalSampah.getText());
            int totalPoint = Integer.parseInt(txtTotalPoint.getText());

            Dropbox newDropbox = new Dropbox(0, namaDropbox, daerahID, totalSampah, totalPoint);
            dropboxDAO.insertDropbox(newDropbox);

            JOptionPane.showMessageDialog(this, "Dropbox berhasil ditambahkan!");
            loadDropboxData();
            clearInputFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding dropbox: " + e.getMessage());
        }
    }

    private void updateDropbox() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin diubah!");
                return;
            }

            int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            String namaDropbox = txtNamaDropbox.getText();
            int daerahID = Integer.parseInt(txtLokasiDropbox.getText());
            double totalSampah = Double.parseDouble(txtTotalSampah.getText());
            int totalPoint = Integer.parseInt(txtTotalPoint.getText());

            Dropbox updatedDropbox = new Dropbox(id, namaDropbox, daerahID, totalSampah, totalPoint);
            dropboxDAO.updateDropbox(updatedDropbox);

            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
            loadDropboxData();
            clearInputFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating dropbox: " + e.getMessage());
        }
    }

    private void deleteDropbox() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
                return;
            }

            int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            dropboxDAO.deleteDropbox(id);

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            loadDropboxData();
            clearInputFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting dropbox: " + e.getMessage());
        }
    }

    private void searchDropbox(String keyword) {
        try {
            List<Dropbox> dropboxList = dropboxDAO.searchDropboxByName(keyword);
            tableModel.setRowCount(0);
            for (Dropbox dropbox : dropboxList) {
                tableModel.addRow(new Object[]{
                        dropbox.getId(),
                        dropbox.getNamaDropBox(),
                        dropbox.getDaerahID(),
                        dropbox.getTotalSampah(),
                        dropbox.getTotalPoint()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
        }
    }

    private void clearInputFields() {
        txtNamaDropbox.setText("");
        txtLokasiDropbox.setText("");
        txtTotalSampah.setText("");
        txtTotalPoint.setText("");
    }
}
