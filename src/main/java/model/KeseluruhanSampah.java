package model;

public class KeseluruhanSampah {
    private int no;
    private String jenisSampah;
    private int kategoriSampahId;
    private double totalSampah;
    private KategoriSampah kategoriSampah; // Pastikan ada objek kategori sampah

    public KeseluruhanSampah(int no, String jenisSampah, int kategoriSampahId, double totalSampah) {
        this.no = no;
        this.jenisSampah = jenisSampah;
        this.kategoriSampahId = kategoriSampahId;
        this.totalSampah = totalSampah;
    }

    
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getJenisSampah() {
        return jenisSampah;
    }

    public void setJenisSampah(String jenisSampah) {
        this.jenisSampah = jenisSampah;
    }

    public int getKategoriSampahId() {
        return kategoriSampahId;
    }

    public void setKategoriSampahId(int kategoriSampahId) {
        this.kategoriSampahId = kategoriSampahId;
    }

    public double getTotalSampah() {
        return totalSampah;
    }

    public void setTotalSampah(double totalSampah) {
        this.totalSampah = totalSampah;
    }

    public KategoriSampah getKategoriSampah() {
        // Implement a method to get the KategoriSampah object based on kategoriSampahId
        return new KategoriSampah(kategoriSampahId, "namaKategori"); // Example, adjust accordingly
    }
}
