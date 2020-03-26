package org.d3ifcool.warehousehelper.tambahdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import org.d3ifcool.warehousehelper.Dashboard.DashboardActivity
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.ActivityTambahDataBinding

class TambahData : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityTambahDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_tambah_data
        )
//        supportActionBar?.title = "Tambah Barang"
        binding.btBackTambahBarang.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        val actionbar = supportActionBar
        actionbar!!.title = "Tambah Barang"
        actionbar.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
