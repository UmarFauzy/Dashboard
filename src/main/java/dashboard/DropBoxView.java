package dashboard;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DropBoxView extends JPanel {

    public DropBoxView() {
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
        Object[][] data = {
            {1, "DropBox A", "Jakarta", 500, 1000},
            {2, "DropBox B", "Bandung", 300, 750},
            {3, "DropBox C", "Surabaya", 450, 900}
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Form input CRUD
        JPanel formPanel = new JPanel();
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Input"));
        formPanel.setLayout(new BorderLayout());

        // Grid untuk form input
        JPanel inputPanel = new JPanel(new GridLayout(3, 4, 10, 10));

        JLabel lblId = new JLabel("ID:");
        JTextField txtId = new JTextField(15);
        JLabel lblNama = new JLabel("Nama Drop Box:");
        JTextField txtNama = new JTextField(15);
        JLabel lblDaerah = new JLabel("Daerah:");
        JTextField txtDaerah = new JTextField(15);
        JLabel lblTotalSampah = new JLabel("Total Sampah:");
        JTextField txtTotalSampah = new JTextField(15);
        JLabel lblTotalPoin = new JLabel("Total Poin:");
        JTextField txtTotalPoin = new JTextField(15);

        inputPanel.add(lblId);
        inputPanel.add(txtId);
        inputPanel.add(lblNama);
        inputPanel.add(txtNama);
        inputPanel.add(lblDaerah);
        inputPanel.add(txtDaerah);
        inputPanel.add(lblTotalSampah);
        inputPanel.add(txtTotalSampah);
        inputPanel.add(lblTotalPoin);
        inputPanel.add(txtTotalPoin);

        // Tombol CRUD
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAdd = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Hapus");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        formPanel.add(inputPanel, BorderLayout.CENTER);
        formPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Tambahkan komponen ke panel utama
        add(filterPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }
}
