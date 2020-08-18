package org.d3ifcool.warehousehelper.adapter

import android.view.View
import org.d3ifcool.warehousehelper.model.DataPeminjaman

interface RecylerViewClickListener2 {
    //fungsi ketika view di klik dan membawa data
    fun onRecyclerViewItemClicked2(view: View, dataPeminjaman: DataPeminjaman)
}