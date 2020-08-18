package org.d3ifcool.warehousehelper.adapter

import android.view.View
import org.d3ifcool.warehousehelper.model.DataInventaris

interface RecyclerViewClickListener {
    //fungsi ketika view di klik dan membawa data
    fun onRecyclerViewItemClicked(view: View, dataInventaris: DataInventaris)
}