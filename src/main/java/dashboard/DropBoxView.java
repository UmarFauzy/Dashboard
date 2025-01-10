package dashboard;

import dao.DropboxDAO;
import dao.DaerahDAO;
import model.Dropbox;
import model.Daerah;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DropBoxView extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNama, txtTotalSampah, txtTotalPoin;
    private JComboBox<Daerah> cbDaerah;

    public DropBoxView(Connection connection) {
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

        // Tabel untuk data Drop Box
        String[] columnNames = {"ID", "Nama Drop Box", "Daerah", "Total Sampah", "Total Poin"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Form input CRUD
        JPanel formPanel = new JPanel();
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Input"));
        formPanel.setLayout(new GridBagLayout());  // Menggunakan GridBagLayout untuk merapikan elemen

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Grid untuk form input
        JLabel lblNama = new JLabel("Nama Drop Box:");
        txtNama = new JTextField(15);

        JLabel lblDaerah = new JLabel("Daerah:");
        cbDaerah = new JComboBox<>();
        loadDaerahData(connection);  // Memuat data Daerah untuk ComboBox

        JLabel lblTotalSampah = new JLabel("Total Sampah:");
        txtTotalSampah = new JTextField(15);

        JLabel lblTotalPoin = new JLabel("Total Poin:");
        txtTotalPoin = new JTextField(15);

        // Menambahkan komponen ke dalam form panel menggunakan GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblNama, gbc);
        gbc.gridx = 1;
        formPanel.add(txtNama, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblDaerah, gbc);
        gbc.gridx = 1;
        formPanel.add(cbDaerah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lblTotalSampah, gbc);
        gbc.gridx = 1;
        formPanel.add(txtTotalSampah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(lblTotalPoin, gbc);
        gbc.gridx = 1;
        formPanel.add(txtTotalPoin, gbc);

        // Tombol CRUD
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAdd = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Hapus");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        // Menambahkan tombol ke form panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // Tambahkan komponen ke panel utama
        add(filterPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);

        // Event listeners untuk CRUD buttons
        btnAdd.addActionListener(e -> addDropbox(connection));
        btnEdit.addActionListener(e -> updateDropbox(connection));
        btnDelete.addActionListener(e -> deleteDropbox(connection));

        // Load data ke tabel
        loadDropboxData(connection);

        // Event listener untuk tabel
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Isi kolom input dengan data dari baris yang dipilih
                txtNama.setText(tableModel.getValueAt(selectedRow, 1).toString());
                cbDaerah.setSelectedItem(tableModel.getValueAt(selectedRow, 2));
                txtTotalSampah.setText(tableModel.getValueAt(selectedRow, 3).toString());
                txtTotalPoin.setText(tableModel.getValueAt(selectedRow, 4).toString());
            }
        });
    }

    private void loadDaerahData(Connection connection) {
        try {
            DaerahDAO daerahDAO = new DaerahDAO(connection);
            List<Daerah> daerahList = daerahDAO.getAllDaerah();

            for (Daerah daerah : daerahList) {
                cbDaerah.addItem(daerah);  // Menambahkan Daerah ke JComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading daerah: " + e.getMessage());
        }
    }

private void loadDropboxData(Connection connection) {
    try {
        DropboxDAO dropboxDAO = new DropboxDAO(connection);
        List<Dropbox> dropboxList = dropboxDAO.getAllDropbox();

        tableModel.setRowCount(0); // Clear previous data

        // Mengambil data Daerah dari DaerahDAO untuk mencocokkan ID
        DaerahDAO daerahDAO = new DaerahDAO(connection);
        List<Daerah> daerahList = daerahDAO.getAllDaerah();
        // Membuat peta untuk ID -> Nama Daerah
        Map<Integer, String> daerahMap = new HashMap<>();
        for (Daerah daerah : daerahList) {
            daerahMap.put(daerah.getId(), daerah.getNamaDaerah());
        }

        // Menambahkan data ke dalam tabel
        for (Dropbox dropbox : dropboxList) {
            String namaDaerah = daerahMap.get(dropbox.getDaerahID()); // Mendapatkan nama daerah berdasarkan ID
            tableModel.addRow(new Object[]{
                    dropbox.getId(),
                    dropbox.getNamaDropBox(),
                    namaDaerah, // Menampilkan nama daerah
                    dropbox.getTotalSampah(),
                    dropbox.getTotalPoint()
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading dropbox data: " + e.getMessage());
    }
}


    private void addDropbox(Connection connection) {
        try {
            String namaDropbox = txtNama.getText();
            Daerah selectedDaerah = (Daerah) cbDaerah.getSelectedItem();
            double totalSampah = Double.parseDouble(txtTotalSampah.getText());
            int totalPoint = Integer.parseInt(txtTotalPoin.getText());

            Dropbox newDropbox = new Dropbox(0, namaDropbox, selectedDaerah.getId(), totalSampah, totalPoint);
            DropboxDAO dropboxDAO = new DropboxDAO(connection);
            dropboxDAO.insertDropbox(newDropbox);

            JOptionPane.showMessageDialog(this, "Dropbox berhasil ditambahkan!");
            loadDropboxData(connection);
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding dropbox: " + e.getMessage());
        }
    }

    private void updateDropbox(Connection connection) {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin diubah!");
                return;
            }

            int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            String namaDropbox = txtNama.getText();
            Daerah selectedDaerah = (Daerah) cbDaerah.getSelectedItem();
            double totalSampah = Double.parseDouble(txtTotalSampah.getText());
            int totalPoint = Integer.parseInt(txtTotalPoin.getText());

            Dropbox updatedDropbox = new Dropbox(id, namaDropbox, selectedDaerah.getId(), totalSampah, totalPoint);
            DropboxDAO dropboxDAO = new DropboxDAO(connection);
            dropboxDAO.updateDropbox(updatedDropbox);

            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
            loadDropboxData(connection);
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating dropbox: " + e.getMessage());
        }
    }

    private void deleteDropbox(Connection connection) {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
                return;
            }

            int id = (int) tableModel.getValueAt(selectedRow, 0);
            DropboxDAO dropboxDAO = new DropboxDAO(connection);
            dropboxDAO.deleteDropbox(id);

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            loadDropboxData(connection);
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting dropbox: " + e.getMessage());
        }
    }

    // Kosongkan kolom input
    private void clearInputFields() {
        txtNama.setText("");
        txtTotalSampah.setText("");
        txtTotalPoin.setText("");
        cbDaerah.setSelectedIndex(0); // Reset ComboBox to the first item
    }
}
