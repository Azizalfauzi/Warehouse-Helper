package org.d3ifcool.warehousehelper.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_tambah_data.*
import kotlinx.android.synthetic.main.alertdialog_success_tambah_data.view.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.ActivityTambahDataBinding
import org.d3ifcool.warehousehelper.model.Data
import org.d3ifcool.warehousehelper.ui.DashboardActivity
import java.util.*

class TambahData : AppCompatActivity() {
    private lateinit var binding: ActivityTambahDataBinding

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
        if (tv_hasil_nama_find.text.isEmpty()) {
            tv_hasil_nama_find.error = "Masukan Nama barang"
            return
        } else if (inp_jumlah_barang_find.text.isEmpty()) {
            inp_jumlah_barang_find.error = "Masukan Jumlah Barang"
            return
        } else if (inp_harga_barang_find.text.isEmpty()) {
            inp_harga_barang_find.error = "Masukan Jumlah Harga"
            return
        } else {
            val namaBarang = tv_hasil_nama_find.text.toString().trim()
            val jumlahBarang = inp_jumlah_barang_find.text.toString().toInt()
            val hargaBarang = inp_harga_barang_find.text.toString().toInt()
            val tanggal_masuk = tv_hasil_tanggal_find.text.toString().trim()

            val ref = FirebaseDatabase.getInstance().getReference("WarehouseHelper")

            val dataId = ref.push().key

            val data = Data(
                dataId!!,
                namaBarang,
                jumlahBarang,
                hargaBarang,
                tanggal_masuk
            )

            ref.child(dataId).setValue(data).addOnCompleteListener {
                alertSuccessDialog()
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
                tv_hasil_tanggal_find.text = ("$mDay/$mMonth/$mYear")
            }, year, month, day
        )
        dpd.show()
    }

    fun isClear() {
        inp_jumlah_barang_find.text.clear()
        tv_hasil_nama_find.text.clear()
        inp_harga_barang_find.text.clear()
        tv_hasil_tanggal_find.text = "DD/MM/YYYY"
    }

    fun alertSuccessDialog() {
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.alertdialog_success_tambah_data, null)
        //alert dialog builder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        //show dialog
        val mAlertDialog = mBuilder.show()

        mDialogView.bt_back_success_tambah_data.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    fun isInRange(a: Int): Boolean {
        return a in 1..10000000
    }
}
