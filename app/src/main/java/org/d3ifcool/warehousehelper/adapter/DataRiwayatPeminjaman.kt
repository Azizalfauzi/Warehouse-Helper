package org.d3ifcool.warehousehelper.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.model.DataPeminjaman

class DataRiwayatPeminjaman(
    val mCtx: Context,
    val layoutResId: Int,
    val dataList: List<DataPeminjaman>
) :
    ArrayAdapter<DataPeminjaman>(mCtx, layoutResId, dataList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)
        val tv_nama_peminjam = view.findViewById<TextView>(R.id.tv_nama_riwayat_peminjaman)
        val tv_nama_barang = view.findViewById<TextView>(R.id.tv_barang_riwayat_peminjam)
        val tv_jumlah_barang = view.findViewById<TextView>(R.id.tv_jumlah_riwayat_peminjaman)
        val bt_detail_riwayat_peminjaman =
            view.findViewById<ImageView>(R.id.bt_detail_riwayat_peminjaman)

        val data = dataList[position]

        tv_nama_peminjam.text = data.nama_peminjam
        tv_nama_barang.text = data.pilihan_barang
        tv_jumlah_barang.text = data.jumlah_barang


        bt_detail_riwayat_peminjaman.setOnClickListener {
            showDetailsDialog(data)
        }
        return view
    }

    fun showDetailsDialog(data: DataPeminjaman) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Detail Data")
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.list_data_riwayat_find, null)

        val tv_nama_riwayat = view.findViewById<TextView>(R.id.tv_nama_riwayat)
        val tv_nim_riwayat = view.findViewById<TextView>(R.id.tv_nim_riwayat)
        val tv_kelas_riwayat = view.findViewById<TextView>(R.id.tv_kelas_riwayat)
        val tv_barang_riwayat = view.findViewById<TextView>(R.id.tv_barangpinjam_riwayat)
        val tv_waktu_peminjaman = view.findViewById<TextView>(R.id.tv_waktupinjam_riwayat)
        val tv_waktu_pengembalian = view.findViewById<TextView>(R.id.tv_waktupengembalian_riwayat)

        tv_nama_riwayat.text = data.nama_peminjam
        tv_nim_riwayat.text = data.nim_peminjam
        tv_kelas_riwayat.text = data.kelas_peminjam
        tv_barang_riwayat.text = data.pilihan_barang
        tv_waktu_peminjaman.text = data.waktu_peminjaman
        tv_waktu_pengembalian.text = data.waktu_pengembalian

        builder.setView(view)

        builder.setNegativeButton("Back") { p0, p1 ->
        }
        val alert = builder.create()
        alert.show()

    }
}