package org.d3ifcool.warehousehelper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.pinjam_recyclerview.view.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.model.DataPeminjaman
import java.util.*

@Suppress("SpellCheckingInspection")
class DataPeminjamanAdapter : RecyclerView.Adapter<DataPeminjamanAdapter.PeminjamanViewModel>(),
    Filterable {
    class PeminjamanViewModel(val view: View) : RecyclerView.ViewHolder(view)

    private var peminjaman = mutableListOf<DataPeminjaman>()

    private var currentpeminjaman = mutableListOf<DataPeminjaman>()

    var listener: RecylerViewClickListener2? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = (
            PeminjamanViewModel(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.pinjam_recyclerview,
                    parent,
                    false
                )
            )
            )


    override fun getItemCount(): Int = currentpeminjaman.size

    override fun onBindViewHolder(holder: PeminjamanViewModel, position: Int) {
        holder.view.tv_nama_peminjaman.text = currentpeminjaman[position].nama_peminjam
        holder.view.tv_nim_peminjaman.text = currentpeminjaman[position].nim_peminjam
        holder.view.tv_kelas_peminjaman.text = currentpeminjaman[position].kelas_peminjam
        holder.view.tv_barang_peminjaman.text = currentpeminjaman[position].pilihan_barang
        holder.view.tv_jumlah_peminjaman.text = currentpeminjaman[position].jumlah_barang.toString()
        holder.view.tv_waktu_peminjaman.text = currentpeminjaman[position].waktu_peminjaman
        holder.view.tv_waktu_pengembalian.text = currentpeminjaman[position].waktu_pengembalian
    }

    fun setPeminjaman(peminjaman: List<DataPeminjaman>) {
        this.peminjaman = peminjaman as MutableList<DataPeminjaman>
        currentpeminjaman = peminjaman
        notifyDataSetChanged()
    }

    fun addPeminjaman(peminjamanData: DataPeminjaman) {
        if (!peminjaman.contains(peminjamanData)) {
            peminjaman.add(peminjamanData)
        } else {
            val index = peminjaman.indexOf(peminjamanData)
            if (peminjamanData.isDelete) {
                peminjaman.removeAt(index)
            } else {
                peminjaman[index] = peminjamanData
            }
        }
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val cari = constraint?.toString()
                cari?.let {
                    val resultList = mutableListOf<DataPeminjaman>()
                    for (rowc in peminjaman) {
                        if (rowc.nama_peminjam!!.toLowerCase(Locale.ROOT).contains(
                                cari.toLowerCase(
                                    Locale.ROOT
                                )
                            )
                        ) {
                            resultList.add(rowc)
                        }
                    }
                    currentpeminjaman = resultList
                } ?: kotlin.run {
                    currentpeminjaman = peminjaman
                }
                val filterResults = FilterResults()
                filterResults.values = currentpeminjaman
                return filterResults
            }

            @Suppress("SpellCheckingInspection", "UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                currentpeminjaman = results?.values as MutableList<DataPeminjaman>
                notifyDataSetChanged()
            }
        }
    }
}