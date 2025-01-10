package model;

public class KategoriSampah {
    private int no;
    private String namaKategori;

    // Constructor
    public KategoriSampah(int no, String namaKategori) {
        this.no = no;
        this.namaKategori = namaKategori;
    }

    // Getter and Setter
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    @Override
    public String toString() {
        return namaKategori; // Nama kategori akan ditampilkan di JComboBox
    }
}
