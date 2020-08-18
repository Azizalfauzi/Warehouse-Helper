package org.d3ifcool.warehousehelper.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataInventaris(
    //model data
    @get:Exclude
    var id: String? = null,
    var nama_barang: String? = null,
    var jumlah_barang: Int? = 0,
    var harga_barang: Int? = 0,
    var tanggal_masuk: String? = null,
    @get:Exclude
    var isDelete: Boolean = false
):Parcelable {
    //fungsi untuk menyamadengankan
    override fun equals(other: Any?): Boolean {
        return if (other is DataInventaris) {
            other.id == id
        } else false
    }
    //fungsi untuk hash id
    override fun hashCode(): Int {
        //        result = 31 * result + (nama_barang?.hashCode() ?: 0)
        return id?.hashCode() ?: 0
    }
}