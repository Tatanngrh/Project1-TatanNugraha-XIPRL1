/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;

/**
 *
 * @author Ttnngrh
 */
public class Transaksi {
    private String noTransaksi;
    private String namaPemesan;
    private String tanggal;
    private String noMeja;
    private ArrayList<Pesanan> pesanan;
    private double uangBayar;
    private double pajak;
    private double totalBayar;
    private double biayaService = 0;

    public Transaksi(String no_Transaksi, String nm_Pemesan, String tanggal, String no_meja) {
        this.noTransaksi = no_Transaksi;
        this.namaPemesan = nm_Pemesan;
        this.tanggal = tanggal;
        this.noMeja = no_meja;

        pesanan = new ArrayList<>();
    }
    public void tambahPesanan(Pesanan pesanan) {
        this.pesanan.add(pesanan);
    }
    //Hilangkan dari Struktur class
    //public Pesanan getPesanan() {return null;}
    public ArrayList<Pesanan> getSemuaPesanan() {
        return pesanan;
    }
    public double hitungTotalBayar() {return 0;}
    public double Kembalian() {return 0;}
    public void cetakStruk() {}
}
