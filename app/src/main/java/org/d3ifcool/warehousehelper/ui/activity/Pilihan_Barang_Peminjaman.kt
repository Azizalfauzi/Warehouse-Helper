package org.d3ifcool.warehousehelper.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_pilihan__barang__peminjaman.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.ActivityPilihanBarangPeminjamanBinding
import org.d3ifcool.warehousehelper.ui.DashboardActivity

class Pilihan_Barang_Peminjaman : AppCompatActivity() {
    private lateinit var binding: ActivityPilihanBarangPeminjamanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_pilihan__barang__peminjaman)

        binding.btBackPilihan.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        binding.btCardProyektor.setOnClickListener {
            val intent = Intent(this, TambahDataPeminjaman::class.java)
            val data: String = "Proyektor"
            intent.putExtra("tv_kirim", data)
            startActivity(intent)
        }
    }
}
