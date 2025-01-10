package model;

public class Dropbox {
    private int id;
    private String namaDropBox;
    private int daerahID;
    private double totalSampah;
    private int totalPoint;

    // Constructor
    public Dropbox(int id, String namaDropBox, int daerahID, double totalSampah, int totalPoint) {
        this.id = id;
        this.namaDropBox = namaDropBox;
        this.daerahID = daerahID;
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

    public String getNamaDropBox() {
        return namaDropBox;
    }

    public void setNamaDropBox(String namaDropBox) {
        this.namaDropBox = namaDropBox;
    }

    public int getDaerahID() {
        return daerahID;
    }

    public void setDaerahID(int daerahID) {
        this.daerahID = daerahID;
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

    @Override
    public String toString() {
        return namaDropBox; // Nama dropbox akan ditampilkan di JComboBox
    }
}
