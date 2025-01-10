package model;

public class User {
    private int id;
    private String namaPengguna;
    private int daerahId;
    private String namaDaerah;
    private double totalSampah;
    private int totalPoint;
    private String nomorHp;

    // Constructor untuk nama daerah
    public User(int id, String namaPengguna, String namaDaerah, double totalSampah, int totalPoint, String nomorHp) {
        this.id = id;
        this.namaPengguna = namaPengguna;
        this.namaDaerah = namaDaerah;
        this.totalSampah = totalSampah;
        this.totalPoint = totalPoint;
        this.nomorHp = nomorHp;
    }

    // Constructor default
    public User(int id, String namaPengguna, int daerahId, double totalSampah, int totalPoint, String nomorHp) {
        this.id = id;
        this.namaPengguna = namaPengguna;
        this.daerahId = daerahId;
        this.totalSampah = totalSampah;
        this.totalPoint = totalPoint;
        this.nomorHp = nomorHp;
    }

    public int getId() {
        return id;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public int getDaerahId() {
        return daerahId;
    }

    public String getNamaDaerah() {
        return namaDaerah;
    }

    public double getTotalSampah() {
        return totalSampah;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public String getNomorHp() {
        return nomorHp;
    }
}
