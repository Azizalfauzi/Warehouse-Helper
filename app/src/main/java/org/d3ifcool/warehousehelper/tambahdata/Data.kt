package org.d3ifcool.warehousehelper.tambahdata

class Data(
    val id: String,
    val nama_barang: String,
    val jumlah_barang: Int,
    val hargaBarang: Int,
    val tanggal_masuk: String
) {
    constructor() : this("", "", 0, 0, "") {

    }
}