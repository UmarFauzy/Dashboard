package model;

public class Daerah {
    private int id;
    private String namaDaerah;

    public Daerah(int id, String namaDaerah) {
        this.id = id;
        this.namaDaerah = namaDaerah;
    }

    public int getId() {
        return id;
    }

    public String getNamaDaerah() {
        return namaDaerah;
    }

    @Override
    public String toString() {
        return namaDaerah; // Nama daerah akan ditampilkan di JComboBox
    }
}
