package org.d3ifcool.warehousehelper.ui.activity

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_tambah_data_peminjaman.*
import kotlinx.android.synthetic.main.alertdialog_success_pinjam_barang.view.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.ActivityTambahDataPeminjamanBinding
import org.d3ifcool.warehousehelper.model.DataPeminjaman
import org.d3ifcool.warehousehelper.ui.DashboardActivity
import java.text.SimpleDateFormat
import java.util.*

class TambahDataPeminjaman : AppCompatActivity() {

    private lateinit var binding: ActivityTambahDataPeminjamanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tambah_data_peminjaman)

        //fungsi tambah_data_peminjaman
        binding.btPinjamBarang.setOnClickListener {
            saveDataPeminjaman()

        }
        binding.btKembaliPinjamBarang.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        //fungsi spinner
        optionSelected()
        optionSelected2()

        //fungsi time_picker
        binding.btWaktuPinjam.setOnClickListener {
            timePicker()
        }
        binding.btWaktuPengembalian.setOnClickListener {
            timePicker2()
        }

        //toolbar
        val actionbar = supportActionBar
        actionbar!!.title = "Pinjam Barang"
        actionbar.setDisplayHomeAsUpEnabled(true)

    }

    //save data peminjaman
    private fun saveDataPeminjaman() {
        if (inp_nama_peminjam.text.isEmpty()) {
            inp_nama_peminjam.error = "Masukan Nama Peminjam"
            return
        } else if (inp_nim_peminjam.text.isEmpty()) {
            inp_nim_peminjam.error = "Masukan Nama Peminjam"
            return
        } else if (inp_kelas_peminjam.text.isEmpty()) {
            inp_kelas_peminjam.error = "Masukan Kelas Peminjam"
        } else {
            val inp_nama = inp_nama_peminjam.text.toString().trim()
            val inp_nim = inp_nim_peminjam.text.toString().trim()
            val kelas = inp_kelas_peminjam.text.toString().trim()
            val pilihan_barang = tv_hasil_pilihan.text.toString()
            val jumlah_pilihan = tv_hasil_jumlah_peminjaman.text.toString()
            val waktu_peminjaman = tv_hasil_waktu_peminjaman.text.toString()
            val waktu_pengembalian = tv_hasil_waktu_pengembalian.text.toString()

            val ref = FirebaseDatabase.getInstance().getReference("PeminjamanBarang")
            val dataId = ref.push().key
            val data_peminjaman =
                DataPeminjaman(
                    dataId!!,
                    inp_nama,
                    inp_nim,
                    kelas,
                    pilihan_barang,
                    jumlah_pilihan,
                    waktu_peminjaman,
                    waktu_pengembalian
                )
            ref.child(dataId).setValue(data_peminjaman).addOnCompleteListener {
                alertSuccessDialog()
            }
            isClear()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
        }
    }

    fun isClear() {
        inp_nama_peminjam.text.clear()
        inp_nim_peminjam.text.clear()
        inp_kelas_peminjam.text.clear()
        tv_hasil_waktu_peminjaman.text = ""
        tv_hasil_waktu_pengembalian.text = ""

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

    //spinner
    private fun optionSelected() {
        val option = binding.spinnerBarang
        val result = binding.tvHasilPilihan

        val options =
            arrayOf("-", "Proyektor", "Kabel Proyektor", "Stop Kontak")

        option.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)

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
                result.text = options.get(position)
            }
        }
    }

    private fun optionSelected2() {
        val option = binding.spinnerJumlah
        val result = binding.tvHasilJumlahPeminjaman

        val options =
            arrayOf("-", "1", "2", "3")

        option.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)

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
                result.text = options.get(position)
            }
        }
    }
}
