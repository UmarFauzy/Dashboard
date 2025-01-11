package dashboard;

import dao.UserDAO;
import model.Daerah;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.List;

public class UserView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNamaPengguna, txtTotalSampah, txtTotalPoint, txtNoHp;
    private JComboBox<Daerah> cbDaerah;
    private UserDAO userDAO;
    private JButton btnCreate, btnUpdate, btnDelete;
    private JTextField txtSearch; // Deklarasi global untuk search field

    public UserView(Connection connection) {
        this.userDAO = connection != null ? new UserDAO(connection) : null;
        setLayout(new BorderLayout());

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter"));

        JLabel lblSearch = new JLabel("Search:");
        txtSearch = new JTextField(20); // Inisialisasi search field
        filterPanel.add(lblSearch);
        filterPanel.add(txtSearch);

        // Table
        String[] columnNames = {"ID", "Nama Pengguna", "Daerah", "Total Sampah", "Total Point", "Nomor HP"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data Pengguna"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNamaPengguna = new JLabel("Nama Pengguna:");
        txtNamaPengguna = new JTextField();
        JLabel lblDaerah = new JLabel("Daerah:");
        cbDaerah = new JComboBox<>();
        JLabel lblTotalSampah = new JLabel("Total Sampah:");
        txtTotalSampah = new JTextField();
        JLabel lblTotalPoint = new JLabel("Total Point:");
        txtTotalPoint = new JTextField();
        JLabel lblNoHp = new JLabel("Nomor HP:");
        txtNoHp = new JTextField();

        btnCreate = new JButton("Tambah");
        btnUpdate = new JButton("Ubah");
        btnDelete = new JButton("Hapus");

        // Add input components
        addInputComponent(inputPanel, gbc, lblNamaPengguna, txtNamaPengguna, 0);
        addInputComponent(inputPanel, gbc, lblDaerah, cbDaerah, 1);
        addInputComponent(inputPanel, gbc, lblTotalSampah, txtTotalSampah, 2);
        addInputComponent(inputPanel, gbc, lblTotalPoint, txtTotalPoint, 3);
        addInputComponent(inputPanel, gbc, lblNoHp, txtNoHp, 4);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(btnCreate, gbc);
        gbc.gridx = 1;
        inputPanel.add(btnUpdate, gbc);
        gbc.gridx = 2;
        inputPanel.add(btnDelete, gbc);

        // Add to main panel
        add(filterPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Load data
        loadDaerahData();
        loadUserData();

        // Button actions
        btnCreate.addActionListener(e -> createUser());
        btnUpdate.addActionListener(e -> {
            if (validateUpdate()) {
                updateUser();
            }
        });
        btnDelete.addActionListener(e -> {
            if (validateDelete()) {
                deleteUser();
            }
        });

        // Event listener untuk tabel
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    fillInputFields(selectedRow);
                }
            }
        });

        // Event listener untuk search field
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtSearch.getText().trim(); // Trim untuk menghapus spasi tambahan
                searchUser(keyword);
            }
        });
    }

    private void addInputComponent(JPanel panel, GridBagConstraints gbc, JLabel label, JComponent component, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(component, gbc);
        component.setPreferredSize(new Dimension(200, 25));
    }

    private void loadUserData() {
        try {
            List<User> users = userDAO.getAllUsersWithDaerah();
            tableModel.setRowCount(0);
            for (User user : users) {
                tableModel.addRow(new Object[]{
                        user.getId(),
                        user.getNamaPengguna(),
                        user.getNamaDaerah(),
                        user.getTotalSampah(),
                        user.getTotalPoint(),
                        user.getNomorHp()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private void loadDaerahData() {
        try {
            cbDaerah.removeAllItems();
            List<Daerah> daerahList = userDAO.getDaerahList();
            for (Daerah daerah : daerahList) {
                cbDaerah.addItem(daerah);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat daerah: " + e.getMessage());
        }
    }

    private void fillInputFields(int selectedRow) {
        txtNamaPengguna.setText((String) tableModel.getValueAt(selectedRow, 1));
        String daerahName = (String) tableModel.getValueAt(selectedRow, 2);
        for (int i = 0; i < cbDaerah.getItemCount(); i++) {
            if (cbDaerah.getItemAt(i).toString().equals(daerahName)) {
                cbDaerah.setSelectedIndex(i);
                break;
            }
        }
        txtTotalSampah.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
        txtTotalPoint.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
        txtNoHp.setText((String) tableModel.getValueAt(selectedRow, 5));
    }

    private void createUser() {
        try {
            Daerah selectedDaerah = (Daerah) cbDaerah.getSelectedItem();
            User user = new User(
                    0,
                    txtNamaPengguna.getText(),
                    selectedDaerah.getId(),
                    Double.parseDouble(txtTotalSampah.getText()),
                    Integer.parseInt(txtTotalPoint.getText()),
                    txtNoHp.getText()
            );
            userDAO.insertUser(user);
            loadUserData();
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data: " + e.getMessage());
        }
    }

    private void updateUser() {
        try {
            int selectedRow = table.getSelectedRow();
            int userId = (int) tableModel.getValueAt(selectedRow, 0);
            Daerah selectedDaerah = (Daerah) cbDaerah.getSelectedItem();
            User user = new User(
                    userId,
                    txtNamaPengguna.getText(),
                    selectedDaerah.getId(),
                    Double.parseDouble(txtTotalSampah.getText()),
                    Integer.parseInt(txtTotalPoint.getText()),
                    txtNoHp.getText()
            );
            userDAO.updateUser(user);
            loadUserData();
            JOptionPane.showMessageDialog(this, "Data berhasil diubah!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah data: " + e.getMessage());
        }
    }

    private void deleteUser() {
        try {
            int selectedRow = table.getSelectedRow();
            int userId = (int) tableModel.getValueAt(selectedRow, 0);
            userDAO.deleteUser(userId);
            loadUserData();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
        }
    }

    private void searchUser(String keyword) {
        try {
            List<User> users = userDAO.searchUserByName(keyword);
            tableModel.setRowCount(0); // Bersihkan data tabel
            for (User user : users) {
                tableModel.addRow(new Object[]{
                        user.getId(),
                        user.getNamaPengguna(),
                        user.getNamaDaerah(),
                        user.getTotalSampah(),
                        user.getTotalPoint(),
                        user.getNomorHp()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
        }
    }

    private boolean validateUpdate() {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data di tabel untuk diubah!");
            return false;
        }
        return true;
    }

    private boolean validateDelete() {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data di tabel untuk dihapus!");
            return false;
        }
        return true;
    }
}
