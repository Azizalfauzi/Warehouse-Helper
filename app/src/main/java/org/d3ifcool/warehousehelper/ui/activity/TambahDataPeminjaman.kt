package org.d3ifcool.warehousehelper.ui.activity

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_tambah_data_peminjaman.*
import kotlinx.android.synthetic.main.alertdialog_success_pinjam_barang.view.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.ActivityTambahDataPeminjamanBinding
import org.d3ifcool.warehousehelper.model.DataPeminjaman
import org.d3ifcool.warehousehelper.ui.DashboardActivity
import org.d3ifcool.warehousehelper.viewmodel.PeminjamanViewModel
import java.text.SimpleDateFormat
import java.util.*

class TambahDataPeminjaman : AppCompatActivity() {
    private lateinit var viewModel: PeminjamanViewModel
    private lateinit var binding: ActivityTambahDataPeminjamanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tambah_data_peminjaman)
//        viewModel = ViewModelProviders.of(this).get(PeminjamanViewModel::class.java)
        viewModel = ViewModelProvider(this).get(PeminjamanViewModel::class.java)
        val dataFetch = intent.getStringExtra("tv_kirim")
        if (dataFetch != null) {
            binding.tvHasilPilihan.text = dataFetch.toString()
        }
        binding.btPinjamBarang.setOnClickListener {
            val namaPeminjam = inp_nama_peminjam.text.toString().trim()
            val nimPeminjam = inp_nim_peminjam.text.toString().trim()
            val kelasPeminjam = inp_kelas_peminjam.text.toString().trim()
            val deskripsiPeminjaman = inp_deskripsi_peminjaman.text.toString().trim()
            if (namaPeminjam.isEmpty()) {
                inp_nama_peminjam.error = "Nama Peminjam Tidak Boleh Kosong!"
                return@setOnClickListener
            } else if (nimPeminjam.isEmpty()) {
                inp_nim_peminjam.error = "Nim Peminjam Tidak Boleh Kosong!"
                return@setOnClickListener
            } else if (kelasPeminjam.isEmpty()) {
                inp_kelas_peminjam.error = "Kelas Peminjam Tidak Boleh Kosong!"
                return@setOnClickListener
            } else if (deskripsiPeminjaman.isEmpty()) {
                inp_deskripsi_peminjaman.error = "Deskripsi Peminjaman Tidak Boleh Kosong!"
                return@setOnClickListener
            } else {
                val nama_peminjam = inp_nama_peminjam.text.toString()
                val nim_peminjam = inp_nim_peminjam.text.toString()
                val kelas_peminjam = inp_kelas_peminjam.text.toString()
                val barang = tv_hasil_pilihan.text.toString()
                val jumlahBarang = tv_hasil_jumlah_peminjaman.text.toString().toInt()
                val jamPeminjaman = tv_hasil_waktu_peminjaman.text.toString()
                val jamPengembalian = tv_hasil_waktu_pengembalian.text.toString()
                val deskripsiPeminjaman = inp_deskripsi_peminjaman.text.toString()

                val dataPeminjaman = DataPeminjaman()
                dataPeminjaman.nama_peminjam = nama_peminjam
                dataPeminjaman.nim_peminjam = nim_peminjam
                dataPeminjaman.kelas_peminjam = kelas_peminjam
                dataPeminjaman.pilihan_barang = barang
                dataPeminjaman.jumlah_barang = jumlahBarang
                dataPeminjaman.waktu_peminjaman = jamPeminjaman
                dataPeminjaman.waktu_pengembalian = jamPengembalian
                dataPeminjaman.deskripsi_peminjaman = deskripsiPeminjaman
                viewModel.addPeminjaman(dataPeminjaman)
                alertSuccessDialog()
            }
        }
        binding.btKembaliPinjamBarang.setOnClickListener {
            startActivity(Intent(this, Pilihan_Barang_Peminjaman::class.java))
        }
        //fungsi spinner
        optionSelected2()

        //fungsi time_picker
        binding.imgWaktuPinjam.setOnClickListener {
            timePicker()
        }
        binding.imgWaktuPengembalian.setOnClickListener {
            timePicker2()
        }
    }


    fun alertSuccessDialog() {
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.alertdialog_success_pinjam_barang, null)
        //alert dialog builder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        //show dialog
        val mAlertDialog = mBuilder.show()

        mDialogView.bt_kembali_pinjam_barang.setOnClickListener {
            mAlertDialog.dismiss()
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }

    //time picker
    private fun timePicker() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal.set(Calendar.MINUTE, minute)
            //set di text_view
            tv_hasil_waktu_peminjaman.text = SimpleDateFormat("HH:mm").format(cal.time)
        }
        TimePickerDialog(
            this,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            false
        ).show()
    }

    private fun timePicker2() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal.set(Calendar.MINUTE, minute)
            //set di text_view
            tv_hasil_waktu_pengembalian.text = SimpleDateFormat("HH:mm").format(cal.time)
        }
        TimePickerDialog(
            this,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            false
        ).show()
    }


    private fun optionSelected2() {
        val option = binding.spinnerJumlah
        val result = binding.tvHasilJumlahPeminjaman

        val options =
            arrayOf(0, 1, 2, 3)

        option.adapter = ArrayAdapter<Int>(this, android.R.layout.simple_list_item_1, options)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                result.text = "Tolong Pilih salah satu"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result.text = options.get(position).toString()
            }
        }
    }
}
