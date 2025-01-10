package dashboard;

import dao.KategoriSampahDAO;
import model.KategoriSampah;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

class KategoriSampahView extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNamaKategori;

    public KategoriSampahView(Connection connection) {
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"No", "Nama Kategori"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Panel input form
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Tambah Kategori Baru"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Membuat komponen input form
        JLabel lblNamaKategori = new JLabel("Nama Kategori:");
        txtNamaKategori = new JTextField(15);

        JButton btnTambah = new JButton("Tambah");
        JButton btnEdit = new JButton("Update");
        JButton btnHapus = new JButton("Hapus");

        // Menambahkan komponen input ke dalam panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(lblNamaKategori, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtNamaKategori, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(btnTambah, gbc);
        gbc.gridx = 1;
        inputPanel.add(btnEdit, gbc);
        gbc.gridx = 2;
        inputPanel.add(btnHapus, gbc);

        // Add action listener for the "Tambah" button
        btnTambah.addActionListener(e -> {
            String namaKategori = txtNamaKategori.getText();

            if (!namaKategori.isEmpty()) {
                // Add a new category to the database
                try {
                    KategoriSampah kategoriSampah = new KategoriSampah(0, namaKategori);
                    KategoriSampahDAO kategoriSampahDAO = new KategoriSampahDAO(connection);
                    kategoriSampahDAO.insertKategoriSampah(kategoriSampah);

                    // Refresh table
                    loadKategoriSampahData(connection);
                    clearInputFields();
                    JOptionPane.showMessageDialog(this, "Kategori Sampah berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error adding kategori sampah: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Add action listener for the "Edit" button
        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String namaKategori = txtNamaKategori.getText();
                if (!namaKategori.isEmpty()) {
                    try {
                        int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                        KategoriSampah kategoriSampah = new KategoriSampah(id, namaKategori);
                        KategoriSampahDAO kategoriSampahDAO = new KategoriSampahDAO(connection);
                        kategoriSampahDAO.updateKategoriSampah(kategoriSampah);

                        // Refresh table
                        loadKategoriSampahData(connection);
                        clearInputFields();
                        JOptionPane.showMessageDialog(this, "Kategori Sampah berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error updating kategori sampah: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin diubah!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Add action listener for the "Hapus" button
        btnHapus.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int no = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    KategoriSampahDAO kategoriSampahDAO = new KategoriSampahDAO(connection);
                    kategoriSampahDAO.deleteKategoriSampah(no);

                    // Refresh table
                    loadKategoriSampahData(connection);
                    clearInputFields();
                    JOptionPane.showMessageDialog(this, "Kategori Sampah berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting kategori sampah: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Add components to the panel
        add(tableScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Load data into table
        loadKategoriSampahData(connection);

        // Event listener for table selection
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                txtNamaKategori.setText(tableModel.getValueAt(selectedRow, 1).toString());
            }
        });
    }

    private void loadKategoriSampahData(Connection connection) {
        try {
            KategoriSampahDAO kategoriSampahDAO = new KategoriSampahDAO(connection);
            List<KategoriSampah> kategoriSampahList = kategoriSampahDAO.getAllKategoriSampah();

            tableModel.setRowCount(0); // Clear previous data
            for (KategoriSampah kategoriSampah : kategoriSampahList) {
                tableModel.addRow(new Object[]{
                        kategoriSampah.getNo(),
                        kategoriSampah.getNamaKategori()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading kategori sampah data: " + e.getMessage());
        }
    }

    private void clearInputFields() {
        txtNamaKategori.setText("");
    }
}
