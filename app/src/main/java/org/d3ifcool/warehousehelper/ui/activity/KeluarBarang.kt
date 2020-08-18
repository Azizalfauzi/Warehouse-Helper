package org.d3ifcool.warehousehelper.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.ActivityKeluarBarangBinding


class KeluarBarang : AppCompatActivity() {
    private lateinit var binding: ActivityKeluarBarangBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_keluar_barang)
    }
}


