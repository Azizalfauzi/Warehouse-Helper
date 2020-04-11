package org.d3ifcool.warehousehelper.keluarbarang

class DataKeluarBarang(
    val id: String,
    val nama_barang_keluar: String,
    val jumlah_barang_keluar: Int,
    val tanggal_keluar_barang: String
) {
    constructor() : this("", "", 0, "")
}
