package model;

public class Kurir {
    private int id;
    private String namaKurir;
    private int totalSampah;
    private int totalPoint;

    // Constructor
    public Kurir(int id, String namaKurir, int totalSampah, int totalPoint) {
        this.id = id;
        this.namaKurir = namaKurir;
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

    public String getNamaKurir() {
        return namaKurir;
    }

    public void setNamaKurir(String namaKurir) {
        this.namaKurir = namaKurir;
    }

    public int getTotalSampah() {
        return totalSampah;
    }

    public void setTotalSampah(int totalSampah) {
        this.totalSampah = totalSampah;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    // Override toString to display Kurir name in ComboBox
    @Override
    public String toString() {
        return namaKurir; // Return kurir name for ComboBox display
    }
}
