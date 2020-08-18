package org.d3ifcool.warehousehelper.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_tambah_data.*
import kotlinx.android.synthetic.main.alertdialog_success_pinjam_barang.view.*
import kotlinx.android.synthetic.main.alertdialog_success_tambah_data.view.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.ActivityTambahDataBinding
import org.d3ifcool.warehousehelper.model.DataInventaris
import org.d3ifcool.warehousehelper.ui.DashboardActivity
import org.d3ifcool.warehousehelper.viewmodel.InventarisViewModel
import java.util.*

class TambahData : AppCompatActivity() {
    private lateinit var binding: ActivityTambahDataBinding
    private lateinit var viewModel: InventarisViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tambah_data)
//        viewModel = ViewModelProviders.of(this).get(InventarisViewModel::class.java)
        viewModel = ViewModelProvider(this).get(InventarisViewModel::class.java)
        //tambah data barang
        binding.btTambahBarang.setOnClickListener {
            val namaBarang = inp_nama_barang_edit.text.toString().trim()
            val jumlahBarang = inp_jumlah_barang_edit.text.toString().trim()
            val hargaBarang = inp_harga_barang.text.toString().trim()
            val tvTanggalMasuk = tv_hasil_tanggal.text.toString()
            if (namaBarang.isEmpty()) {
                inp_nama_barang_edit.error = "Inputan nama barang tidak boleh kosong!"
                return@setOnClickListener
            } else if (jumlahBarang.isEmpty()) {
                inp_jumlah_barang_edit.error = "Inputan jumlah barang tidak boleh kosong!"
                return@setOnClickListener
            } else if (hargaBarang.isEmpty()) {
                inp_harga_barang.error = "Inputan harga barang tidak boleh kosong!"
                return@setOnClickListener
            } else {
                val nama_Barang = inp_nama_barang_edit.text.toString()
                val jumlah_Barang = inp_jumlah_barang_edit.text.toString().toInt()
                val harga_Barang = inp_harga_barang.text.toString().toInt()
                val tvTanggal_Masuk = tv_hasil_tanggal.text.toString()
                val dataInventaris = DataInventaris()
                dataInventaris.nama_barang = nama_Barang
                dataInventaris.jumlah_barang = jumlah_Barang
                dataInventaris.harga_barang = harga_Barang
                dataInventaris.tanggal_masuk = tvTanggal_Masuk
                viewModel.addInventaris(dataInventaris)
                alertSuccessDialog()
            }
        }
        //back button
        binding.btBackTambahBarang.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        //date picker
        binding.tvTanggalPilihan.setOnClickListener {
            datePicker()
        }
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
            startActivity(Intent(this, DashboardActivity::class.java))
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
}

