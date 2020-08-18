package org.d3ifcool.warehousehelper.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataKeluarBarang(
    @get:Exclude
    var id: String? = null,
    var nama_barang: String? = null,
    var jumlah_barang: Int? = 0,
    @get:Exclude
    var isDelete: Boolean = false
): Parcelable {
    override fun equals(other: Any?): Boolean {
        return if (other is DataInventaris) {
            other.id == id
        } else false
    }

    override fun hashCode(): Int {
        //        result = 31 * result + (nama_barang?.hashCode() ?: 0)
        return id?.hashCode() ?: 0
    }
}