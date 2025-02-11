package model;

public class Daerah {
    private int id;
    private String namaDaerah;
    private double totalSampah;
    private int totalPoint;

    // Constructor
    public Daerah(int id, String namaDaerah, double totalSampah, int totalPoint) {
        this.id = id;
        this.namaDaerah = namaDaerah;
        this.totalSampah = totalSampah;
        this.totalPoint = totalPoint;
    }

    // Getter and Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaDaerah() {
        return namaDaerah;
    }

    public void setNamaDaerah(String namaDaerah) {
        this.namaDaerah = namaDaerah;
    }

    public double getTotalSampah() {
        return totalSampah;
    }

    public void setTotalSampah(double totalSampah) {
        this.totalSampah = totalSampah;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    // Override toString() to display the Nama Daerah in JComboBox
    @Override
    public String toString() {
        return namaDaerah;
    }
}
