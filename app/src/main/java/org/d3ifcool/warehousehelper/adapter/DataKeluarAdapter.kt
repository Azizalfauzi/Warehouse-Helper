package org.d3ifcool.warehousehelper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_kurang_barang.view.*
import kotlinx.android.synthetic.main.keluar_recyclerview.view.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.model.DataInventaris
import org.d3ifcool.warehousehelper.model.DataKeluarBarang
import java.util.*

@Suppress("SpellCheckingInspection")
class DataKeluarAdapter : RecyclerView.Adapter<DataKeluarAdapter.DataKeluarViewModel>(),
    Filterable {
    private var inventaris = mutableListOf<DataInventaris>()

    private var currentInventaris = mutableListOf<DataInventaris>()


    class DataKeluarViewModel(val view: View) : RecyclerView.ViewHolder(view)

    var listener: RecyclerViewClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = DataKeluarViewModel(
        LayoutInflater.from(parent.context).inflate(R.layout.keluar_recyclerview, parent, false)
    )

    override fun getItemCount(): Int = currentInventaris.size

    override fun onBindViewHolder(holder: DataKeluarViewModel, position: Int) {
        holder.view.tv_nama_barang_keluar.text =currentInventaris[position].nama_barang
        holder.view.tv_quantitiy.text = currentInventaris[position].jumlah_barang.toString()
        holder.view.bt_keluar_barang_recyclerview.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it,currentInventaris[position])
        }
        holder.view.tv_keluar_barang_recyclerview.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it,currentInventaris[position])
        }
    }

    fun setdataInventaris(dataInventaris: List<DataInventaris>) {
        //untuk data awal
        this.inventaris = dataInventaris as MutableList<DataInventaris>
        currentInventaris = dataInventaris
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
                    currentInventaris = resultList
                } ?: kotlin.run {
                    currentInventaris = inventaris
                }
                val filterResult = FilterResults()
                filterResult.values = currentInventaris
                return filterResult
            }

            @Suppress("SpellCheckingInspection", "UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                currentInventaris = results?.values as MutableList<DataInventaris>
                notifyDataSetChanged()
            }
        }
    }
}