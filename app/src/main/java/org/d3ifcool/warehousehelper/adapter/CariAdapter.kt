package org.d3ifcool.warehousehelper.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.model.Data


class CariAdapter(val mCtx: Context, val layoutResId: Int, val dataList: List<Data>) :
    ArrayAdapter<Data>(mCtx, layoutResId, dataList) {
    //    private var data = Data()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        //read data target
        val tv_nama_barang_update = view.findViewById<TextView>(R.id.tv_nama_barang_update)
        val tv_jumlah_barang_update = view.findViewById<TextView>(R.id.tv_jumlah_barang_update)
        val bt_update_barang = view.findViewById<ImageView>(R.id.bt_update_cari_barang)
        val bt_delete_barangg = view.findViewById<ImageView>(R.id.bt_delete_data)

        val data = dataList[position]

        tv_nama_barang_update.text = data.nama_barang
        tv_jumlah_barang_update.text = data.jumlah_barang.toString()
        //button update
        bt_update_barang.setOnClickListener {
            showUpdateDialog(data)
        }
        bt_delete_barangg.setOnClickListener {
            showDeleteDialog(data)
        }
        return view
    }

    fun showDeleteDialog(data: Data) {
        val builder = AlertDialog.Builder(mCtx)
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.list_data_cari_delete, null)

        builder.setView(view)
        builder.setPositiveButton("Delete") { p0, p1 ->
            FirebaseDatabase.getInstance().reference
                .child("WarehouseHelper")
                .child(data.id)
                .removeValue()
            Toast.makeText(context, "Data Barang Berhasil Terhapus", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { p0, p1 ->

        }
        val alert = builder.create()
        alert.show()
    }

    fun showUpdateDialog(data: Data) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Update Data")
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.list_data_cari_update, null)

        //inputan yang akan di edit
        val inp_nama_update = view.findViewById<EditText>(R.id.tv_hasil_nama_find)
        val inp_jumlah_update = view.findViewById<EditText>(R.id.inp_jumlah_barang_find)
        val inp_harga_update = view.findViewById<EditText>(R.id.inp_harga_barang_find)
        val tanggal_masuk_update = view.findViewById<TextView>(R.id.tv_hasil_tanggal_find)

        inp_nama_update.setText(data.nama_barang)
        inp_jumlah_update.setText(data.jumlah_barang.toString())
        inp_harga_update.setText(data.hargaBarang.toString())
        tanggal_masuk_update.text = data.tanggal_masuk

        builder.setView(view)

        builder.setPositiveButton(
            "Update"
        ) { p0, p1 ->
            val dbData = FirebaseDatabase.getInstance().getReference("WarehouseHelper")

            val nama_barang = inp_nama_update.text.toString().trim()
            val jumlah_barang = inp_jumlah_update.text.toString().toInt()
            val harga_barang = inp_harga_update.text.toString().toInt()
            val tanggal_masuk = tanggal_masuk_update.text.toString().trim()

            if (nama_barang.isEmpty()) {
                Toast.makeText(
                    context,
                    "Inputan Nama Barang Tidak Boleh Kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (inp_jumlah_update.text.isEmpty()) {
                Toast.makeText(
                    context,
                    "Inputan Jumlah Barang Tidak Boleh Kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (inp_harga_update.text.isEmpty()) {
                Toast.makeText(
                    context,
                    "Inputan Harga Barang Tidak Boleh Kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val data = Data(
                    data.id,
                    nama_barang,
                    jumlah_barang,
                    harga_barang,
                    tanggal_masuk
                )
                dbData.child(data.id).setValue(data)
                Toast.makeText(mCtx, "Data Berhasil di Update!", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton(
            "No"
        ) { p0, p1 ->

        }
        val alert = builder.create()
        alert.show()
    }
}