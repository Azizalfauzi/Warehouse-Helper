package org.d3ifcool.warehousehelper.keluarbarang

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_keluar_barang.*
import kotlinx.android.synthetic.main.activity_tambah_data.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.ActivityKeluarBarangBinding
import org.d3ifcool.warehousehelper.tambahdata.Data
import java.util.*

class KeluarBarang : AppCompatActivity() {
    private lateinit var binding: ActivityKeluarBarangBinding
    var total: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_keluar_barang)

        binding.btKeluarBarangTransaksi.setOnClickListener {
            if (inp_nama_keluar_barang.text.isEmpty()) {
                inp_nama_keluar_barang.error = "Nama Barang Tidak Boleh Kosong"
                return@setOnClickListener
            } else if (inp_jumlah_keluar_barang.text.isEmpty()) {
                inp_jumlah_keluar_barang.error = "Jumlah Barang Tidak Boleh Kosong"
                return@setOnClickListener
            } else {
                keluarBarang()
            }

        }
        binding.btTanggalKeluarBarang.setOnClickListener {
            datePicker()
        }
        //toolbar
        val actionbar = supportActionBar
        actionbar!!.title = "Keluar Barang"
        actionbar.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    fun keluarBarang() {
        var jumlah_barang_keluar = inp_jumlah_keluar_barang.text.toString().toInt()
        FirebaseDatabase.getInstance().reference
            .child("WarehouseHelper")
            .orderByChild("nama_barang").equalTo(inp_nama_keluar_barang.text.toString().trim())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var map = p0.children.first().value as Map<String, Int>
                    var id = map["id"].toString()
                    var jumlah_barang = map["jumlah_barang"].toString().toInt()

                    total = jumlah_barang - jumlah_barang_keluar

                    var map2 = mutableMapOf<String, Int>()
                    map2["jumlah_barang"] = total.toString().toInt()

                    FirebaseDatabase.getInstance().reference
                        .child("WarehouseHelper")
                        .child(id)
                        .updateChildren(map2 as Map<String, Int>)
                    Toast.makeText(applicationContext, "Transaksi Berhasil!!", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun datePicker() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                tv_tanggal_keluar_barang.text = ("$mDay/$mMonth/$mYear")
            }, year, month, day
        )
        dpd.show()
    }

    fun dataTransaksi() {

    }
}
