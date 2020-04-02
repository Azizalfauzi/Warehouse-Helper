package org.d3ifcool.warehousehelper.pinjambarang

class DataPeminjaman(
    val id: String,
    val nama_peminjam: String,
    val nim_peminjam: String,
    val kelas_peminjam: String,
    val pilihan_barang: String,
    val jumlah_barang: String,
    val waktu_peminjaman: String,
    val waktu_pengembalian: String
) {
    constructor() : this("", "", "", "", "", "", "", "")
}