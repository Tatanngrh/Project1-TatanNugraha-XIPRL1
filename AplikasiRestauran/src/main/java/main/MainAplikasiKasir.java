/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import classes.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Ttnngrh
 */
public class MainAplikasiKasir {
    public DaftarMenu daftarmenu;
    //Tambahkan
    public static double PAJAK_PPN = 0.10;
    public static double BIAYA_SERVICE = 0.05;
    //End of Tambahkan
    
    public static void main(String[] args) {
        //init
        Scanner input = new Scanner(System.in);
        //Tambahkan
        String no_transaksi, nama_pemesan, tanggal, no_meja = "";
        String transaksi_lagi = "", pesan_lagi = "", keterangan = "", makan_ditempat;
        int jumlah_pesanan, no_menu;
        //End of Tambahkan
        MainAplikasiKasir app = new MainAplikasiKasir();
        app.generateDaftarMenu();
        //Mulai Transaksi
        System.out.println("============ TRANSAKSI ============");

        //Ambil data Transaksi
        System.out.print("No Transaksi : ");
        no_transaksi = input.next();
        System.out.print("Pemesan : ");
        nama_pemesan = input.next();
        System.out.print("Tanggal : [dd-mm-yyyy] \t");
        tanggal = input.next();
        System.out.print("Makan ditempat? [Y/N]");
        makan_ditempat = input.next();

        if (makan_ditempat.equalsIgnoreCase("Y")) {
            System.out.print("Nomor Meja : ");
            no_meja = input.next();
        }
        //buat Transaksi Baru
        Transaksi trans = new Transaksi(no_transaksi, nama_pemesan, tanggal, no_meja);
        System.out.println("============ PESANAN ============");
        int nokuah;
        do {
            //ambil menu berdasarkan nomor urut yang dipilih
            Menu menu_yang_dipilih = app.daftarmenu.pilihMenu();

            jumlah_pesanan = (int) app.cekInputNumber("Jumlah : ");

            //Buat Pesanan
            Pesanan pesanan = new Pesanan(menu_yang_dipilih, jumlah_pesanan);
            trans.tambahPesanan(pesanan);

            //Khusus untuk menu ramen, pesanan kuahnya langsung diinput juga
            if (menu_yang_dipilih.getKategori().equals("Ramen")) {
                //Looping sesuai jumlah pesanan ramen
                int jumlah_ramen = jumlah_pesanan;
                do {
                    //Ambil objek menu berdasarkan nomor yang dipilih
                    Menu kuah_yang_dipilih = app.daftarmenu.pilihKuah();

                    System.out.print("Level: [0-5] : ");
                    String level = input.next();

                    //Validasi jumlah kuah tidak boleh lebih besar dari jumlahRamen
                    int jumlah_kuah = 0;
                    do {
                        jumlah_kuah = (int) app.cekInputNumber("Jumlah : ");
                        if (jumlah_kuah > jumlah_ramen) {
                            System.out.println("[Err] Jumlah kuah melebihi jumlah ramen yang sudah dipesan");
                        } else {
                            break;
                        }
                    } while (jumlah_kuah > jumlah_ramen);

                    //Set Pesanan kuah
                    Pesanan pesanan_kuah = new Pesanan(kuah_yang_dipilih, jumlah_kuah);
                    pesanan_kuah.setKeterangan("Level " + level);

                    //Tambahkan pesanan kuah ke transaksi
                    trans.tambahPesanan(pesanan_kuah);

                //Hitung jumlah ramen yang belum dipesan kuahnya
                jumlah_ramen -= jumlah_kuah;
            } while (jumlah_ramen > 0);

        } else {
            System.out.print("Keterangan [- Jika kosong]: ");
            keterangan = input.next();
        }

        //Cek jika keterangan diisi selain "-" set ke pesanan
        if (!keterangan.equals("-")) {
        pesanan.setKeterangan(keterangan);
        }

        //konfirmasi, mau tambah pesanan atau tidak
        System.out.print("Tambah Pesanan Lagi? [Y/N] : ");
        pesan_lagi = input.next();
        } while (pesan_lagi.equalsIgnoreCase("Y"));
        
        //Cetak Struk
        trans.cetakStruk();

        //Hitung total harga
        double totalPesanan = trans.hitungTotalPesanan();
        System.out.println("========================");
        System.out.println("Total : \t\t" + totalPesanan);

        //Hitung Pajak
        //Jika makan ditempat, biaya pajak = 10% ppn + 5% service
        trans.setPajak(PAJAK_PPN);
        double ppn = trans.hitungPajak();
        System.out.println("Pajak 10% : \t\t" + ppn);

        double biaya_service = 0;
        if (makan_ditempat.equalsIgnoreCase("Y")) {
            trans.setBiayaService(BIAYA_SERVICE);
            biaya_service = trans.hitungBiayaService();
            System.out.println("Biaya Service 5% : \t" + biaya_service);
        }

        //Tampilkan total bayar
        System.out.println("Total : \t\t" + trans.hitungTotalBayar(ppn, biaya_service));

        //Cek uang bayar, apakah > total bayar atau tidak
        double kembalian = 0;
        do {
            double uangBayar = app.cekInputNumber("Uang Bayar : \t\t");

            kembalian = trans.hitungKembalian(uangBayar);
            if (kembalian < 0) {
                System.out.println("[Err] Uang anda kurang");
            } else {
                System.out.println("Kembalian : \t\t" + kembalian);
                break;
            }
        } while (kembalian < 0);

        System.out.println("============ TERIMA KASIH ============");
    }
    public void generateDaftarMenu() {
        DaftarMenu daftarMenu = new DaftarMenu();
        //Membuat Menu Ramen
        daftarMenu.tambahMenu(new Ramen("Ramen Seafood", 25000));
        daftarMenu.tambahMenu(new Ramen("Ramen Original", 18000));
        daftarMenu.tambahMenu(new Ramen("Ramen Vegetarian", 22000));
        daftarMenu.tambahMenu(new Ramen("Ramen Karnivor", 28000));

        //Membuat Menu Kuah
        daftarMenu.tambahMenu(new Kuah("Kuah Orisinil"));
        daftarMenu.tambahMenu(new Kuah("Kuah Internasional"));
        daftarMenu.tambahMenu(new Kuah("Kuah Spicy Lada"));
        daftarMenu.tambahMenu(new Kuah("Kuah Soto Padang"));

        //Membuat Menu Toping
        daftarMenu.tambahMenu(new Toping("Crab Stick Bakar", 6000));
        daftarMenu.tambahMenu(new Toping("Chicken Katsu", 8000));
        daftarMenu.tambahMenu(new Toping("Gyoza Goreng", 4000));
        daftarMenu.tambahMenu(new Toping("Bakso Goreng", 7000));
        daftarMenu.tambahMenu(new Toping("Enoki Goreng", 5000));

        //Membuat Menu Minuman
        daftarMenu.tambahMenu(new Minuman("Jus Aplukat SPC", 10000));
        daftarMenu.tambahMenu(new Minuman("Jus Stroberi", 11000));
        daftarMenu.tambahMenu(new Minuman("Cappucino Coffee", 15000));
        daftarMenu.tambahMenu(new Minuman("Vietnam Dripp", 14000));

        daftarMenu.tampilDaftarMenu();
    }
    public double cekInputNumber(String label) {
        try {
            Scanner getInput = new Scanner(System.in);
            System.out.print(label);
            double nilai = getInput.nextDouble();

            return nilai;
        } catch (InputMismatchException ex){
            System.out.println("[Err] Harap Masukkan angka");
            return cekInputNumber(label);
        }
    }
}
