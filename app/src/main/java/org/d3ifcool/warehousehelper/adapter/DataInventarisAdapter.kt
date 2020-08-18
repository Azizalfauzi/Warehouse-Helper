package org.d3ifcool.warehousehelper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.inventaris_recyclerview.view.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.model.DataInventaris
import java.util.*

@Suppress("SpellCheckingInspection")
class DataInventarisAdapter :
    RecyclerView.Adapter<DataInventarisAdapter.DataInventarisViewModel>(), Filterable {
    //data inventaris
    private var inventaris = mutableListOf<DataInventaris>()
    //menampung filter
    private var currentinventaris = mutableListOf<DataInventaris>()

    var listener: RecyclerViewClickListener? = null

    class DataInventarisViewModel(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataInventarisViewModel(
            LayoutInflater.from(parent.context).inflate(
                R.layout.inventaris_recyclerview,
                parent,
                false
            )
        )


    override fun getItemCount(): Int = currentinventaris.size

    override fun onBindViewHolder(
        holder: DataInventarisViewModel,
        position: Int
    ) {
        holder.view.tv_nama_inventaris.text = currentinventaris[position].nama_barang
        holder.view.tv_jumlah_inventaris.text = currentinventaris[position].jumlah_barang.toString()
        holder.view.tv_harga_inventaris.text = currentinventaris[position].harga_barang.toString()
        holder.view.tv_tanggal_inventaris.text = currentinventaris[position].tanggal_masuk
        holder.view.bt_delete_recyclerview.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, currentinventaris[position])
        }
        holder.view.bt_edit_recyclerview.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, currentinventaris[position])
        }
    }


    fun setdataInventaris(dataInventaris: List<DataInventaris>) {
        //untuk data awal
        this.inventaris = dataInventaris as MutableList<DataInventaris>
        currentinventaris = dataInventaris
        notifyDataSetChanged()
    }

    fun adddataInventaris(dataInventaris: DataInventaris) {
        //untuk menambahkan data
        if (!inventaris.contains(dataInventaris)) {
            inventaris.add(dataInventaris)
        } else {
            //untuk update data
            val index = inventaris.indexOf(dataInventaris)
            if (dataInventaris.isDelete) {
                inventaris.removeAt(index)
            } else {
                inventaris[index] = dataInventaris
            }
        }
    }

    //fungsi searc recyclerview
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                //tampung data yang di cari
                val cari = constraint?.toString()
                cari?.let {
                    val resultList = mutableListOf<DataInventaris>()
                    for (row in inventaris) {
                        if (row.nama_barang!!.toLowerCase(Locale.ROOT).contains(
                                cari.toLowerCase(
                                    Locale.ROOT
                                )
                            )
                        ) {
                            resultList.add(row)
                        }
                    }
                    currentinventaris = resultList
                } ?: kotlin.run {
                    currentinventaris = inventaris
                }
                val filterResult = FilterResults()
                filterResult.values = currentinventaris
                return filterResult
            }

            @Suppress("SpellCheckingInspection", "UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                currentinventaris = results?.values as MutableList<DataInventaris>
                notifyDataSetChanged()
            }
        }
    }
}