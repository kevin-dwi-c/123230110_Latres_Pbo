
package Controller;

import DAO.DAO_Transaksi;
import Model.ModelTransaksi;
import View.ViewTransaksi;
import java.util.List;


public class TransaksiController {
    private ViewTransaksi view;
    private DAO_Transaksi DAOtransaksi = new DAO_Transaksi();
    private List<ModelTransaksi> data;
    private ModelTransaksi selectedTransaksi;

    public TransaksiController(ViewTransaksi view){
        this.view = view;
        refresh();
    }
    
    private void refresh(){
        data = DAOtransaksi.getAllTransaksi();
        view.setTabel(data);
        updateSelected(-1);
    }
    
    public void insertTransaksi(String namaPelanggan, String namaObat, int hargaSatuan, int jumlahBeli) {
        var transaksi = new ModelTransaksi(0, namaPelanggan, namaObat, hargaSatuan, jumlahBeli);
        if (DAOtransaksi.isDuplicate(namaPelanggan, namaObat)) {
            view.showError("Data duplikat, sudah pernah dimasukkan!");
        return;
        }
        if(DAOtransaksi.insertTransaksi(transaksi)) {
            refresh();
        } else {
            view.showError("Data Gagal Ditambahkan");
        }
    }
    
    public void updateSelected(int index) {
        if (index == -1) {
            selectedTransaksi = null;
        } else {
            selectedTransaksi = data.get(index);
        }
        view.setSelected(selectedTransaksi);
    }
    
    public void edit(String namaPelanggan, String namaObat, int hargaSatuan, int jumlahBeli) {
        if(selectedTransaksi == null){
            view.showError("Belum ada Yang Dipilih");
            return;
        }
        var transaksi = new ModelTransaksi(selectedTransaksi.getId(), namaPelanggan, namaObat, hargaSatuan, jumlahBeli);
        if (DAOtransaksi.updateTransaksi(transaksi)){           
            refresh();
        } else {
            view.showError("User Gagal Ditambahkan");
        }
    }
    
    public void delete() {
        if(selectedTransaksi == null){
            view.showError("Belum ada Yang Dipilih");
            return;
        }
        if (DAOtransaksi.deleteTransaksi(selectedTransaksi.getId())){           
            refresh();
        } else {
            view.showError("User Gagal Ditambahkan");
        }
    }
    
    public void showTotalBayar(int index) {
        if (index < 0 || index >= data.size()) {
            view.showError("Index transaksi tidak valid!");
            return;
        }

        ModelTransaksi transaksi = data.get(index);
        int total = transaksi.getHargaSatuan() * transaksi.getJumlahBeli();
        int diskon = (transaksi.getJumlahBeli() > 5) ? total / 10 : 0;
        int totalBayar = total - diskon;

        String message = "Harga satuan: Rp " + transaksi.getHargaSatuan()
                   + "\nJumlah beli: " + transaksi.getJumlahBeli()
                   + "\nTotal: Rp " + total
                   + (diskon > 0 ? "\nDiskon 10%: Rp " + diskon : "")
                   + "\nTotal Bayar: Rp " + totalBayar;

    view.showMessage(message);
    }
    
}
