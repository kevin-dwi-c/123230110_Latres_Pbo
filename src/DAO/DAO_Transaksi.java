package DAO;

import Model.ConnectDB;
import Model.ModelTransaksi;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO_Transaksi {

    public boolean insertTransaksi(ModelTransaksi t) {
        var conn = ConnectDB.getConnection();
        try (var stmt = conn.prepareStatement("INSERT INTO transaksi "
                + "(nama_pelanggan, nama_obat, harga_satuan, jumlah_beli)"
                + " VALUES (?, ?, ?, ?)");) {
            stmt.setString(1, t.getNamaPelanggan());
            stmt.setString(2, t.getNamaObat());
            stmt.setInt(3, t.getHargaSatuan());
            stmt.setInt(4, t.getJumlahBeli());
            stmt.execute();
        }catch(Exception e){
             e.printStackTrace();
        }
         return true;
    }

    public List<ModelTransaksi> getAllTransaksi(){
         var conn = ConnectDB.getConnection();
         var result = new ArrayList<ModelTransaksi>();
        try (var stmt = conn.prepareStatement("SELECT * FROM transaksi");){
             stmt.execute();
            var rs = stmt.getResultSet();
            while(rs.next()){
                ModelTransaksi transaksi = new ModelTransaksi(
                rs.getInt("id"),
                rs.getString("nama_pelanggan"),
                rs.getString("nama_obat"),
                rs.getInt("harga_satuan"),
                rs.getInt("jumlah_beli")
            );
            result.add(transaksi);
            }
        }catch(Exception e){
             e.printStackTrace();
        }
        return result;
    }

    public boolean updateTransaksi(ModelTransaksi t){
         var conn = ConnectDB.getConnection();
        try (var stmt = conn.prepareStatement("UPDATE transaksi SET "
                + "nama_pelanggan=?, nama_obat=?,"
                + " harga_satuan=?, jumlah_beli=? "
                + "WHERE id=?");) {
            stmt.setString(1, t.getNamaPelanggan());
            stmt.setString(2, t.getNamaObat());
            stmt.setInt(3, t.getHargaSatuan());
            stmt.setInt(4, t.getJumlahBeli());
            stmt.setInt(5, t.getId());
            stmt.execute();
        }catch(Exception e){
             e.printStackTrace();
        }
         return true;
    }

    public boolean deleteTransaksi(int id){
        var conn = ConnectDB.getConnection();
        try (var stmt = conn.prepareStatement("DELETE FROM transaksi WHERE id=?");){
            stmt.setInt(1, id);
            stmt.execute();
        }catch(Exception e){
             e.printStackTrace();
        }
         return true;
    }
    
    public boolean isDuplicate(String namaPelanggan, String namaObat) {
    var conn = ConnectDB.getConnection();
    try (var stmt = conn.prepareStatement("SELECT COUNT(*) FROM transaksi WHERE nama_pelanggan = ? AND nama_obat = ?")) {
        stmt.setString(1, namaPelanggan);
        stmt.setString(2, namaObat);
        var rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

}
