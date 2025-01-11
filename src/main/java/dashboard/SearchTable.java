package dashboard;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;


public class SearchTable {
    public static void main(String[] args) {
        // Frame utama
        JFrame frame = new JFrame("Search Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Data tabel
        String[][] data = {
            {"10", "Umar", "Bandung", "1000.0", "20000", "082119303251"},
            {"11", "Zacky", "Jakarta", "100.0", "1000", "082119303030"}
        };
        String[] columnNames = {"ID", "Nama Pengguna", "Daerah", "Total Sampah", "Total Point", "Nomor HP"};

        // Tabel dan model
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        // Panel pencarian
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search: ");
        JTextField searchField = new JTextField();

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        // Tambahkan filter pada JTextField
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = searchField.getText();
                if (text.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        // Tambahkan komponen ke frame
        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Atur ukuran dan tampilkan
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}
