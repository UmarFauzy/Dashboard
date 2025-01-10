package dashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeseluruhanSampahView extends JPanel {
        
    public KeseluruhanSampahView() {
        setLayout(new BorderLayout());

        // Create filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter"));

        JLabel lblFilterKategori = new JLabel("Kategori:");
        JComboBox<String> cbKategori = new JComboBox<>(new String[]{"Semua", "Plastik", "Kertas", "Logam"});
        JLabel lblFilterSort = new JLabel("Sortir:");
        JComboBox<String> cbSort = new JComboBox<>(new String[]{"Top", "Bottom"});

        filterPanel.add(lblFilterKategori);
        filterPanel.add(cbKategori);
        filterPanel.add(lblFilterSort);
        filterPanel.add(cbSort);

        // Create table
        String[] columnNames = {"No", "Jenis Sampah", "Kategori Sampah", "Total Sampah"};
        Object[][] data = {
            {1, "Botol Plastik", "Plastik", 100},
            {2, "Koran", "Kertas", 50},
            {3, "Kaleng Minuman", "Logam", 30}
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Add components to the panel
        add(filterPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Add action listeners for filters
        cbKategori.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Filter logic based on selected category
                String selectedCategory = (String) cbKategori.getSelectedItem();
                System.out.println("Filter by category: " + selectedCategory);
            }
        });

        cbSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sort logic based on top or bottom selection
                String selectedSort = (String) cbSort.getSelectedItem();
                System.out.println("Sort by: " + selectedSort);
            }
        });
    }
}
