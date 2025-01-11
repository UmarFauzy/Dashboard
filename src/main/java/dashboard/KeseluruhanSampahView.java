package dashboard;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import dao.KeseluruhanSampahDAO;
import dao.KategoriSampahDAO;
import model.KategoriSampah;
import model.KeseluruhanSampah;
import javax.swing.table.DefaultTableModel;

public class KeseluruhanSampahView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtJenisSampah, txtTotalSampah;
    private JComboBox<KategoriSampah> cbKategoriSampah;

    public KeseluruhanSampahView(Connection connection) {
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

        // Tabel untuk data Keseluruhan Sampah
        String[] columnNames = {"No", "Jenis Sampah", "Kategori Sampah", "Total Sampah"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Form input CRUD
        JPanel formPanel = new JPanel();
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Input"));
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Grid untuk form input
        JLabel lblJenisSampah = new JLabel("Jenis Sampah:");
        txtJenisSampah = new JTextField(15);
        JLabel lblKategoriSampah = new JLabel("Kategori Sampah:");
        cbKategoriSampah = new JComboBox<>();
        loadKategoriSampahData(connection);
        JLabel lblTotalSampah = new JLabel("Total Sampah:");
        txtTotalSampah = new JTextField(15);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblJenisSampah, gbc);
        gbc.gridx = 1; formPanel.add(txtJenisSampah, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblKategoriSampah, gbc);
        gbc.gridx = 1; formPanel.add(cbKategoriSampah, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(lblTotalSampah, gbc);
        gbc.gridx = 1; formPanel.add(txtTotalSampah, gbc);

        // Tombol CRUD
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAdd = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Hapus");
        buttonPanel.add(btnAdd); buttonPanel.add(btnEdit); buttonPanel.add(btnDelete);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(filterPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addKeseluruhanSampah(connection));
        btnEdit.addActionListener(e -> updateKeseluruhanSampah(connection));
        btnDelete.addActionListener(e -> deleteKeseluruhanSampah(connection));

        loadKeseluruhanSampahData(connection);
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                txtJenisSampah.setText(tableModel.getValueAt(selectedRow, 1).toString());
                cbKategoriSampah.setSelectedItem(tableModel.getValueAt(selectedRow, 2));
                txtTotalSampah.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        });
    }

    private void loadKategoriSampahData(Connection connection) {
        try {
            KategoriSampahDAO kategoriSampahDAO = new KategoriSampahDAO(connection);
            List<KategoriSampah> kategoriSampahList = kategoriSampahDAO.getAllKategoriSampah();
            cbKategoriSampah.removeAllItems(); // Clear previous items
            for (KategoriSampah kategori : kategoriSampahList) {
                cbKategoriSampah.addItem(kategori);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading kategori sampah: " + e.getMessage());
        }
    }
    

    private void loadKeseluruhanSampahData(Connection connection) {
        try {
            KeseluruhanSampahDAO keseluruhanSampahDAO = new KeseluruhanSampahDAO(connection);
            KategoriSampahDAO kategoriSampahDAO = new KategoriSampahDAO(connection); // Ambil kategori
            List<KeseluruhanSampah> keseluruhanSampahList = keseluruhanSampahDAO.getAllKeseluruhanSampah();
    
            tableModel.setRowCount(0); // Clear previous data
            for (KeseluruhanSampah sampah : keseluruhanSampahList) {
                KategoriSampah kategoriSampah = kategoriSampahDAO.getKategoriSampahByNo(sampah.getKategoriSampahId()); // Ambil nama kategori
                String namaKategori = kategoriSampah != null ? kategoriSampah.getNamaKategori() : "Tidak Ditemukan";
                tableModel.addRow(new Object[]{
                    sampah.getNo(),
                    sampah.getJenisSampah(),
                    namaKategori,
                    sampah.getTotalSampah()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading keseluruhan sampah data: " + e.getMessage());
        }
    }
    

    private void addKeseluruhanSampah(Connection connection) {
        try {
            String jenisSampah = txtJenisSampah.getText();
            KategoriSampah selectedKategori = (KategoriSampah) cbKategoriSampah.getSelectedItem();
            double totalSampah = Double.parseDouble(txtTotalSampah.getText());
            KeseluruhanSampah newSampah = new KeseluruhanSampah(0, jenisSampah, selectedKategori.getNo(), totalSampah);
            KeseluruhanSampahDAO keseluruhanSampahDAO = new KeseluruhanSampahDAO(connection);
            keseluruhanSampahDAO.insertKeseluruhanSampah(newSampah);
            JOptionPane.showMessageDialog(this, "Data keseluruhan sampah berhasil ditambahkan!");
            loadKeseluruhanSampahData(connection);
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding keseluruhan sampah: " + e.getMessage());
        }
    }
    

    private void updateKeseluruhanSampah(Connection connection) {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin diubah!");
                return;
            }
            int no = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            String jenisSampah = txtJenisSampah.getText();
            KategoriSampah selectedKategori = (KategoriSampah) cbKategoriSampah.getSelectedItem();
            double totalSampah = Double.parseDouble(txtTotalSampah.getText());
            KeseluruhanSampah updatedSampah = new KeseluruhanSampah(no, jenisSampah, selectedKategori.getNo(), totalSampah);
            KeseluruhanSampahDAO keseluruhanSampahDAO = new KeseluruhanSampahDAO(connection);
            keseluruhanSampahDAO.updateKeseluruhanSampah(updatedSampah);
            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
            loadKeseluruhanSampahData(connection);
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating keseluruhan sampah: " + e.getMessage());
        }
    }
    

    private void deleteKeseluruhanSampah(Connection connection) {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
                return;
            }
            int no = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            KeseluruhanSampahDAO keseluruhanSampahDAO = new KeseluruhanSampahDAO(connection);
            keseluruhanSampahDAO.deleteKeseluruhanSampah(no);
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            loadKeseluruhanSampahData(connection);
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting keseluruhan sampah: " + e.getMessage());
        }
    }

    private void clearInputFields() {
        txtJenisSampah.setText("");
        txtTotalSampah.setText("");
        cbKategoriSampah.setSelectedIndex(0);
    }
}
