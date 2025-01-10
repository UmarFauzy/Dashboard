package dashboard;

import dao.KurirDAO;
import model.Kurir;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class KurirView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNamaKurir, txtTotalSampah, txtTotalPoint;

    public KurirView(Connection connection) {
        setLayout(new BorderLayout());

        // Panel Tabel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama Kurir", "Total Sampah", "Total Point"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        // Panel Form Input
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Input Data Kurir"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nama Kurir
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nama Kurir:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        txtNamaKurir = new JTextField(20);
        formPanel.add(txtNamaKurir, gbc);

        // Total Sampah
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Total Sampah:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtTotalSampah = new JTextField(20);
        formPanel.add(txtTotalSampah, gbc);

        // Total Point
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Total Point:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        txtTotalPoint = new JTextField(20);
        formPanel.add(txtTotalPoint, gbc);

        // Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAdd = new JButton("Tambah");
        JButton btnUpdate = new JButton("Ubah");
        JButton btnDelete = new JButton("Hapus");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.SOUTH);

        // Event Listener untuk Tabel
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Ambil data dari baris yang dipilih dan tampilkan di kolom input
                txtNamaKurir.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtTotalSampah.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtTotalPoint.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        });

        // Event Listener untuk Tombol CRUD
        btnAdd.addActionListener(e -> addKurir(connection));
        btnUpdate.addActionListener(e -> updateKurir(connection));
        btnDelete.addActionListener(e -> deleteKurir(connection));

        // Load data ke tabel
        loadKurirData(connection);
    }

    private void loadKurirData(Connection connection) {
        try {
            KurirDAO kurirDAO = new KurirDAO(connection);
            List<Kurir> kurirList = kurirDAO.getAllKurir();

            tableModel.setRowCount(0);
            for (Kurir kurir : kurirList) {
                tableModel.addRow(new Object[]{
                        kurir.getId(),
                        kurir.getNamaKurir(),
                        kurir.getTotalSampah(),
                        kurir.getTotalPoint()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void addKurir(Connection connection) {
        try {
            String namaKurir = txtNamaKurir.getText();
            int totalSampah = Integer.parseInt(txtTotalSampah.getText());
            int totalPoint = Integer.parseInt(txtTotalPoint.getText());

            Kurir newKurir = new Kurir(0, namaKurir, totalSampah, totalPoint);
            KurirDAO kurirDAO = new KurirDAO(connection);
            kurirDAO.insertKurir(newKurir);

            JOptionPane.showMessageDialog(this, "Kurir berhasil ditambahkan!");
            loadKurirData(connection);

            // Kosongkan input setelah menambah data
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding kurir: " + e.getMessage());
        }
    }

    private void updateKurir(Connection connection) {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin diubah!");
                return;
            }

            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String namaKurir = txtNamaKurir.getText();
            int totalSampah = Integer.parseInt(txtTotalSampah.getText());
            int totalPoint = Integer.parseInt(txtTotalPoint.getText());

            Kurir updatedKurir = new Kurir(id, namaKurir, totalSampah, totalPoint);
            KurirDAO kurirDAO = new KurirDAO(connection);
            kurirDAO.updateKurir(updatedKurir);

            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
            loadKurirData(connection);

            // Kosongkan input setelah memperbarui data
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating kurir: " + e.getMessage());
        }
    }

    private void deleteKurir(Connection connection) {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
                return;
            }

            int id = (int) tableModel.getValueAt(selectedRow, 0);
            KurirDAO kurirDAO = new KurirDAO(connection);
            kurirDAO.deleteKurir(id);

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            loadKurirData(connection);

            // Kosongkan input setelah menghapus data
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting kurir: " + e.getMessage());
        }
    }

    // Kosongkan kolom input
    private void clearInputFields() {
        txtNamaKurir.setText("");
        txtTotalSampah.setText("");
        txtTotalPoint.setText("");
    }
}
