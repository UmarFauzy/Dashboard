package dashboard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class KurirView extends JPanel {

    public KurirView() {
        setLayout(new BorderLayout());

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter"));

        JLabel lblSearch = new JLabel("Search:");
        JTextField txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(200, 30)); // Adjust size
        JLabel lblSort = new JLabel("Sortir:");
        JComboBox<String> cbSort = new JComboBox<>(new String[]{"Top", "Bottom"});
        cbSort.setPreferredSize(new Dimension(120, 30)); // Adjust size

        filterPanel.add(lblSearch);
        filterPanel.add(txtSearch);
        filterPanel.add(lblSort);
        filterPanel.add(cbSort);

        // Table
        String[] columnNames = {"ID", "Nama Kurir", "Sampah Dijemput", "Total Point"};
        Object[][] data = {
            {1, "Adi Nugroho", 150, 3000},
            {2, "Budi Santoso", 120, 2500},
            {3, "Citra Dewi", 100, 2000}
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Input form
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data Kurir"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblID = new JLabel("ID:");
        JTextField txtID = new JTextField();
        txtID.setPreferredSize(new Dimension(300, 30)); // Adjust size

        JLabel lblNamaKurir = new JLabel("Nama Kurir:");
        JTextField txtNamaKurir = new JTextField();
        txtNamaKurir.setPreferredSize(new Dimension(300, 30)); // Adjust size

        JLabel lblSampahDijemput = new JLabel("Sampah Dijemput:");
        JTextField txtSampahDijemput = new JTextField();
        txtSampahDijemput.setPreferredSize(new Dimension(300, 30)); // Adjust size

        JLabel lblTotalPoint = new JLabel("Total Point:");
        JTextField txtTotalPoint = new JTextField();
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
        inputPanel.add(lblID, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtID, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lblNamaKurir, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtNamaKurir, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(lblSampahDijemput, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtSampahDijemput, gbc);

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
    }
}


