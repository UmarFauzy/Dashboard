package dashboard;

import dao.*;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class RiwayatTransaksiView extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JSpinner tanggalSpinner;
    private JTextField txtTotalSampah, txtTotalPoint;
    private JComboBox<Dropbox> cbDropBox;
    private JComboBox<Daerah> cbDaerah;
    private JComboBox<User> cbUser;
    private JComboBox<Kurir> cbKurir;
    private JComboBox<KeseluruhanSampah> cbSampah;
    private JComboBox<KategoriSampah> cbKategori;
    private JComboBox<String> cbNoHandphone;

    private HashMap<Integer, String> userMap = new HashMap<>();
    private HashMap<Integer, String> kurirMap = new HashMap<>();
    private HashMap<Integer, String> sampahMap = new HashMap<>();
    private HashMap<Integer, String> kategoriMap = new HashMap<>();
    private HashMap<Integer, String> dropboxMap = new HashMap<>();
    private HashMap<Integer, String> daerahMap = new HashMap<>();

    public RiwayatTransaksiView(Connection connection) {
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

        // Tabel untuk data Riwayat Transaksi
        String[] columnNames = {"ID", "Tanggal", "User ID", "Kurir ID", "Sampah ID", "Kategori ID", "Total Sampah", "Total Point", "DropBox ID", "Daerah ID", "No Handphone"};
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

        // Define input fields
        JLabel lblTanggal = new JLabel("Tanggal:");
        tanggalSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(tanggalSpinner, "yyyy-MM-dd");
        tanggalSpinner.setEditor(dateEditor);
        tanggalSpinner.setValue(new java.util.Date());

        JLabel lblUserID = new JLabel("User :");
        cbUser = new JComboBox<>();
        loadUserData(connection);

        JLabel lblKurirID = new JLabel("Kurir :");
        cbKurir = new JComboBox<>();
        loadKurirData(connection);

        JLabel lblSampahID = new JLabel("Sampah :");
        cbSampah = new JComboBox<>();
        loadSampahData(connection);

        JLabel lblKategoriID = new JLabel("Kategori :");
        cbKategori = new JComboBox<>();
        loadKategoriData(connection);

        JLabel lblTotalSampah = new JLabel("Total Sampah :");
        txtTotalSampah = new JTextField(15);

        JLabel lblTotalPoint = new JLabel("Total Point :");
        txtTotalPoint = new JTextField(15);

        JLabel lblDropBox = new JLabel("DropBox:");
        cbDropBox = new JComboBox<>();
        loadDropboxData(connection);

        JLabel lblDaerah = new JLabel("Daerah:");
        cbDaerah = new JComboBox<>();
        loadDaerahData(connection);

        JLabel lblNoHandphone = new JLabel("No Handphone :");
        cbNoHandphone = new JComboBox<>();
        loadNoHandphoneData(connection);

        // Adding components to formPanel using GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblTanggal, gbc);
        gbc.gridx = 1;
        formPanel.add(tanggalSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblUserID, gbc);
        gbc.gridx = 1;
        formPanel.add(cbUser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lblKurirID, gbc);
        gbc.gridx = 1;
        formPanel.add(cbKurir, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(lblSampahID, gbc);
        gbc.gridx = 1;
        formPanel.add(cbSampah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(lblKategoriID, gbc);
        gbc.gridx = 1;
        formPanel.add(cbKategori, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(lblTotalSampah, gbc);
        gbc.gridx = 1;
        formPanel.add(txtTotalSampah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(lblTotalPoint, gbc);
        gbc.gridx = 1;
        formPanel.add(txtTotalPoint, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(lblDropBox, gbc);
        gbc.gridx = 1;
        formPanel.add(cbDropBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(lblDaerah, gbc);
        gbc.gridx = 1;
        formPanel.add(cbDaerah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(lblNoHandphone, gbc);
        gbc.gridx = 1;
        formPanel.add(cbNoHandphone, gbc);

        // CRUD buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAdd = new JButton("Tambah");
        JButton btnEdit = new JButton("Ubah");
        JButton btnDelete = new JButton("Hapus");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        // Add buttons to formPanel
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // Add components to main panel
        add(filterPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);

        // Event listeners for CRUD buttons
        btnAdd.addActionListener(e -> addRiwayatTransaksi(connection));
        btnEdit.addActionListener(e -> updateRiwayatTransaksi(connection));
        btnDelete.addActionListener(e -> deleteRiwayatTransaksi(connection));

        // Load data to table
        loadRiwayatTransaksiData(connection);

        // Table selection listener to populate input fields
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tanggalSpinner.setValue(tableModel.getValueAt(selectedRow, 1));
                cbUser.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
                cbKurir.setSelectedItem(tableModel.getValueAt(selectedRow, 3).toString());
                cbSampah.setSelectedItem(tableModel.getValueAt(selectedRow, 4).toString());
                cbKategori.setSelectedItem(tableModel.getValueAt(selectedRow, 5).toString());
                txtTotalSampah.setText(tableModel.getValueAt(selectedRow, 6).toString());
                txtTotalPoint.setText(tableModel.getValueAt(selectedRow, 7).toString());
                cbDropBox.setSelectedItem(tableModel.getValueAt(selectedRow, 8)); // Populate DropBox ComboBox
                cbDaerah.setSelectedItem(tableModel.getValueAt(selectedRow, 9)); // Populate Daerah ComboBox
                cbNoHandphone.setSelectedItem(tableModel.getValueAt(selectedRow, 10).toString());
            }
        });
    }

    private void loadUserData(Connection connection) {
        try {
            UserDAO userDAO = new UserDAO(connection);
            List<User> userList = userDAO.getAllUsersWithDaerah();
            for (User user : userList) {
                cbUser.addItem(user);
                userMap.put(user.getId(), user.getNamaPengguna());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading User data: " + e.getMessage());
        }
    }

    private void loadKurirData(Connection connection) {
        try {
            KurirDAO kurirDAO = new KurirDAO(connection);
            List<Kurir> kurirList = kurirDAO.getAllKurir();
            for (Kurir kurir : kurirList) {
                cbKurir.addItem(kurir);
                kurirMap.put(kurir.getId(), kurir.getNamaKurir());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading Kurir data: " + e.getMessage());
        }
    }

    private void loadSampahData(Connection connection) {
        try {
            KeseluruhanSampahDAO sampahDAO = new KeseluruhanSampahDAO(connection);
            List<KeseluruhanSampah> sampahList = sampahDAO.getAllKeseluruhanSampah();
            for (KeseluruhanSampah sampah : sampahList) {
                cbSampah.addItem(sampah);
                sampahMap.put(sampah.getNo(), sampah.getJenisSampah());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading Sampah data: " + e.getMessage());
        }
    }

    private void loadKategoriData(Connection connection) {
        try {
            KategoriSampahDAO kategoriDAO = new KategoriSampahDAO(connection);
            List<KategoriSampah> kategoriList = kategoriDAO.getAllKategoriSampah();
            for (KategoriSampah kategori : kategoriList) {
                cbKategori.addItem(kategori);
                kategoriMap.put(kategori.getNo(), kategori.getNamaKategori());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading Kategori data: " + e.getMessage());
        }
    }

    private void loadNoHandphoneData(Connection connection) {
        try {
            UserDAO userDAO = new UserDAO(connection);
            List<String> noHandphoneList = userDAO.getAllNoHandphone();

            // Hapus item yang ada sebelumnya di ComboBox
            cbNoHandphone.removeAllItems();

            // Tambahkan nomor HP ke dalam ComboBox
            for (String noHandphone : noHandphoneList) {
                cbNoHandphone.addItem(noHandphone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading No Handphone data: " + e.getMessage());
        }
    }

    private void loadDropboxData(Connection connection) {
        try {
            DropboxDAO dropboxDAO = new DropboxDAO(connection);
            List<Dropbox> dropboxList = dropboxDAO.getAllDropbox();
            for (Dropbox dropbox : dropboxList) {
                cbDropBox.addItem(dropbox);
                dropboxMap.put(dropbox.getId(), dropbox.getNamaDropBox());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading Dropbox data: " + e.getMessage());
        }
    }

    private void loadDaerahData(Connection connection) {
        try {
            DaerahDAO daerahDAO = new DaerahDAO(connection);
            List<Daerah> daerahList = daerahDAO.getAllDaerah();
            for (Daerah daerah : daerahList) {
                cbDaerah.addItem(daerah);
                daerahMap.put(daerah.getId(), daerah.getNamaDaerah());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading Daerah data: " + e.getMessage());
        }
    }

    private void loadRiwayatTransaksiData(Connection connection) {
        try {
            // Hapus semua data di model tabel
            tableModel.setRowCount(0);

            RiwayatTransaksiDAO riwayatDAO = new RiwayatTransaksiDAO(connection);
            List<RiwayatTransaksi> listTransaksi = riwayatDAO.getAllRiwayatTransaksi();

            // Tambahkan data terbaru ke tabel
            for (RiwayatTransaksi transaksi : listTransaksi) {
                tableModel.addRow(new Object[]{
                    transaksi.getId(),
                    transaksi.getTanggal(),
                    userMap.get(transaksi.getUserId()), // Tampilkan nama user
                    kurirMap.get(transaksi.getKurirId()), // Tampilkan nama kurir
                    sampahMap.get(transaksi.getSampahId()), // Tampilkan nama sampah
                    kategoriMap.get(transaksi.getKategoriId()), // Tampilkan nama kategori
                    transaksi.getTotalSampah(),
                    transaksi.getTotalPoint(),
                    dropboxMap.get(transaksi.getDropBoxId()), // Tampilkan nama Dropbox
                    daerahMap.get(transaksi.getDaerahId()), // Tampilkan nama Daerah
                    transaksi.getNoHandphone()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading transaction data: " + e.getMessage());
        }
    }

    private void addRiwayatTransaksi(Connection connection) {
        try {
            // Mengambil tanggal dari JSpinner
            java.util.Date selectedDate = (java.util.Date) tanggalSpinner.getValue();
            java.sql.Date tanggal = new java.sql.Date(selectedDate.getTime());

            RiwayatTransaksi riwayatTransaksi = new RiwayatTransaksi(
                    0, tanggal,
                    ((User) cbUser.getSelectedItem()).getId(),
                    ((Kurir) cbKurir.getSelectedItem()).getId(),
                    ((KeseluruhanSampah) cbSampah.getSelectedItem()).getNo(),
                    ((KategoriSampah) cbKategori.getSelectedItem()).getNo(),
                    Double.parseDouble(txtTotalSampah.getText()),
                    Integer.parseInt(txtTotalPoint.getText()),
                    ((Dropbox) cbDropBox.getSelectedItem()).getId(),
                    ((Daerah) cbDaerah.getSelectedItem()).getId(),
                    ((String) cbNoHandphone.getSelectedItem())
            );

            RiwayatTransaksiDAO riwayatDAO = new RiwayatTransaksiDAO(connection);
            riwayatDAO.insertRiwayatTransaksi(riwayatTransaksi);
            JOptionPane.showMessageDialog(this, "Transaksi berhasil ditambahkan.");
            loadRiwayatTransaksiData(connection);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding transaction: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input tidak valid: " + e.getMessage());
        }
    }

    private void updateRiwayatTransaksi(Connection connection) {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (Integer) tableModel.getValueAt(selectedRow, 0); // Ambil ID dari baris yang dipilih
                System.out.println("ID untuk Update: " + id); // Debugging untuk verifikasi ID

                // Mengambil tanggal dari JSpinner
                java.util.Date selectedDate = (java.util.Date) tanggalSpinner.getValue();
                java.sql.Date tanggal = new java.sql.Date(selectedDate.getTime());

                // Buat objek transaksi dengan data yang diubah
                RiwayatTransaksi riwayatTransaksi = new RiwayatTransaksi(
                        id, tanggal, ((User) cbUser.getSelectedItem()).getId(), ((Kurir) cbKurir.getSelectedItem()).getId(),
                        ((KeseluruhanSampah) cbSampah.getSelectedItem()).getNo(),
                        ((KategoriSampah) cbKategori.getSelectedItem()).getNo(),
                        Double.parseDouble(txtTotalSampah.getText()), Integer.parseInt(txtTotalPoint.getText()),
                        ((Dropbox) cbDropBox.getSelectedItem()).getId(), ((Daerah) cbDaerah.getSelectedItem()).getId(),
                        ((String) cbNoHandphone.getSelectedItem())
                );

                RiwayatTransaksiDAO riwayatDAO = new RiwayatTransaksiDAO(connection);
                riwayatDAO.updateRiwayatTransaksi(riwayatTransaksi);
                JOptionPane.showMessageDialog(this, "Transaksi berhasil diubah.");
                loadRiwayatTransaksiData(connection);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris yang akan diubah.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating transaction: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input tidak valid: " + e.getMessage());
        }
    }

    private void deleteRiwayatTransaksi(Connection connection) {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (Integer) tableModel.getValueAt(selectedRow, 0); // Ambil ID dari baris yang dipilih
                System.out.println("ID untuk Delete: " + id); // Debugging untuk verifikasi ID

                RiwayatTransaksiDAO riwayatDAO = new RiwayatTransaksiDAO(connection);
                riwayatDAO.deleteRiwayatTransaksi(id);
                JOptionPane.showMessageDialog(this, "Transaksi berhasil dihapus.");
                loadRiwayatTransaksiData(connection); // Refresh data di tabel
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting transaction: " + e.getMessage());
        }
    }
}
