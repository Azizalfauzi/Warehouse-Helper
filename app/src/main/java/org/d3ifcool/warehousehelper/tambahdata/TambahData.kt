package org.d3ifcool.warehousehelper.tambahdata

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_tambah_data.*
import org.d3ifcool.warehousehelper.Dashboard.DashboardActivity
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.ActivityTambahDataBinding
import java.util.*

class TambahData : AppCompatActivity() {
    private lateinit var binding: ActivityTambahDataBinding
    private var total = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_tambah_data
        )

        //tambah data barang
        binding.btTambahBarang.setOnClickListener {
            saveData()
        }

        //back button
        binding.btBackTambahBarang.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        //date picker
        binding.tvTanggalPilihan.setOnClickListener {
            datePicker()
        }


        //toolbar
        val actionbar = supportActionBar
        actionbar!!.title = "Tambah Barang"
        actionbar.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun saveData() {
        if (inp_nama_barang.text.isEmpty()) {
            inp_nama_barang.error = "Masukan Nama barang"
            return
        } else if (inp_jumlah_barang.text.isEmpty()) {
            inp_jumlah_barang.error = "Masukan Jumlah Barang"
            return
        } else if (inp_harga_barang.text.isEmpty()) {
            inp_harga_barang.error = "Masukan Jumlah Harga"
            return
        } else {
            val namaBarang = inp_nama_barang.text.toString().trim()
            val jumlahBarang = inp_jumlah_barang.text.toString().toInt()
            val hargaBarang = inp_harga_barang.text.toString().toInt()
            val tanggal_masuk = tv_hasil_tanggal.text.toString().trim()

            val ref = FirebaseDatabase.getInstance().getReference("WarehouseHelper")

            val dataId = ref.push().key

            val data = Data(dataId!!, namaBarang, jumlahBarang, hargaBarang, tanggal_masuk)

            ref.child(dataId).setValue(data).addOnCompleteListener {
                Toast.makeText(applicationContext, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT)
                    .show()
            }
            isClear()
        }
    }

    private fun datePicker() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                tv_hasil_tanggal.text = ("$mDay/$mMonth/$mYear")
            }, year, month, day
        )
        dpd.show()
    }

    fun isClear() {
        inp_jumlah_barang.text.clear()
        inp_nama_barang.text.clear()
        inp_harga_barang.text.clear()
        tv_hasil_tanggal.text="DD/MM/YYYY"
    }

    fun isInRange(a: Int): Boolean {
        return a in 1..10000000
    }
}
