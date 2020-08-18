package org.d3ifcool.warehousehelper.model

import com.google.firebase.database.Exclude


data class DataPeminjaman(
    @get:Exclude
    var id: String? = null,
    var nama_peminjam: String? = null,
    var nim_peminjam: String? = null,
    var kelas_peminjam: String? = null,
    var pilihan_barang: String? = null,
    var jumlah_barang: Int? = 0,
    var waktu_peminjaman: String? = null,
    var waktu_pengembalian: String? = null,
    var deskripsi_peminjaman: String? = null,
    @get:Exclude
    var isDelete: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return if (other is DataPeminjaman) {
            other.id == id
        } else false
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
