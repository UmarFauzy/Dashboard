package model;

import java.util.Date;

public class RiwayatTransaksi {

    private int id;
    private Date tanggal;
    private int userId;
    private int kurirId;
    private int sampahId;
    private int kategoriId;
    private double totalSampah;
    private int totalPoint;
    private int dropBoxId;
    private int daerahId;
    private String noHandphone;

    public RiwayatTransaksi(int id, Date tanggal, int userId, int kurirId, int sampahId, int kategoriId,
            double totalSampah, int totalPoint, int dropBoxId, int daerahId, String noHandphone) {
        this.id = id;
        this.tanggal = tanggal;
        this.userId = userId;
        this.kurirId = kurirId;
        this.sampahId = sampahId;
        this.kategoriId = kategoriId;
        this.totalSampah = totalSampah;
        this.totalPoint = totalPoint;
        this.dropBoxId = dropBoxId;
        this.daerahId = daerahId;
        this.noHandphone = noHandphone;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getKurirId() {
        return kurirId;
    }

    public void setKurirId(int kurirId) {
        this.kurirId = kurirId;
    }

    public int getSampahId() {
        return sampahId;
    }

    public void setSampahId(int sampahId) {
        this.sampahId = sampahId;
    }

    public int getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(int kategoriId) {
        this.kategoriId = kategoriId;
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

    public int getDropBoxId() {
        return dropBoxId;
    }

    public void setDropBoxId(int dropBoxId) {
        this.dropBoxId = dropBoxId;
    }

    public int getDaerahId() {
        return daerahId;
    }

    public void setDaerahId(int daerahId) {
        this.daerahId = daerahId;
    }

    public String getNoHandphone() {
        return noHandphone;
    }

    public void setNoHandphone(String noHandphone) {
        this.noHandphone = noHandphone;
    }

    @Override
    public String toString() {
        return "RiwayatTransaksi{"
                + "id=" + id
                + ", tanggal=" + tanggal
                + ", userId=" + userId
                + ", kurirId=" + kurirId
                + ", sampahId=" + sampahId
                + ", kategoriId=" + kategoriId
                + ", totalSampah=" + totalSampah
                + ", totalPoint=" + totalPoint
                + ", dropBoxId=" + dropBoxId
                + ", daerahId=" + daerahId
                + ", noHandphone='" + noHandphone + '\''
                + '}';
    }
}
