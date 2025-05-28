package Model;

public class ModelTransaksi {

    private int id;
    private String namaPelanggan;
    private String namaObat;
    private int hargaSatuan;
    private int jumlahBeli;

    public ModelTransaksi(int id, String namaPelanggan, String namaObat, int hargaSatuan, int jumlahBeli) {
        this.id = id;
        this.namaPelanggan = namaPelanggan;
        this.namaObat = namaObat;
        this.hargaSatuan = hargaSatuan;
        this.jumlahBeli = jumlahBeli;
    }

    public int getId() {
        return id;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public int getHargaSatuan() {
        return hargaSatuan;
    }

    public int getJumlahBeli() {
        return jumlahBeli;
    }

}
