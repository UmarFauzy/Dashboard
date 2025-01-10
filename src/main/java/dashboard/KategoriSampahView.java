package dashboard;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class KategoriSampahView extends JPanel {

    public KategoriSampahView() {
        setLayout(new BorderLayout());

        // Create table
        String[] columnNames = {"No", "Nama Kategori"};
        Object[][] data = {
            {1, "Plastik"},
            {2, "Kertas"},
            {3, "Logam"}
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Create input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Tambah Kategori Baru"));

        JLabel lblNo = new JLabel("No:");
        JTextField txtNo = new JTextField(5);
        JLabel lblNamaKategori = new JLabel("Nama Kategori:");
        JTextField txtNamaKategori = new JTextField(15);
        JButton btnTambah = new JButton("Tambah");

        inputPanel.add(lblNo);
        inputPanel.add(txtNo);
        inputPanel.add(lblNamaKategori);
        inputPanel.add(txtNamaKategori);
        inputPanel.add(btnTambah);

        // Add action listener for the "Tambah" button
        btnTambah.addActionListener(e -> {
            String no = txtNo.getText();
            String namaKategori = txtNamaKategori.getText();

            if (!no.isEmpty() && !namaKategori.isEmpty()) {
                // Add a new row to the table
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{Integer.parseInt(no), namaKategori});

                // Clear input fields
                txtNo.setText("");
                txtNamaKategori.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Add components to the panel
        add(tableScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

    }
}
