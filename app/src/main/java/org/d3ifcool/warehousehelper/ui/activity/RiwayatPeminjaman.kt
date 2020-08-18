package org.d3ifcool.warehousehelper.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.pinjam_recyclerview.view.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.ActivityRiwayatPeminjamanBinding
import org.d3ifcool.warehousehelper.model.DataPeminjaman
import org.d3ifcool.warehousehelper.ui.DashboardActivity
import org.d3ifcool.warehousehelper.viewmodel.PeminjamanViewModel
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class RiwayatPeminjaman : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityRiwayatPeminjamanBinding

    //viewmodel
    private lateinit var viewModel: PeminjamanViewModel

    //reusable adapter
    private lateinit var adapter: ReusableAdapter<DataPeminjaman>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_riwayat_peminjaman)

        binding.btBackPeminjaman.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        viewModel = ViewModelProvider(this).get(PeminjamanViewModel::class.java)

        //panggil fungsi viewmodel fetch data
        viewModel.fetchPeminjaman()

        //panggil fungsi get real time database
        viewModel.getRealtimeDatabase()

        //init adapter
        adapter = ReusableAdapter(this)
        //setup adapter
        setupAdapter(binding.rvRiwayatData)

        //set observer untuk adapter list
        viewModel.dataPeminjaman.observe({ lifecycle }, {
            adapter.addData(it)
        })
        //set observe untuk adapter list dia sendiri
        viewModel.peminjaman.observe({ lifecycle }, {
            adapter.updateData(it)
        })
        val searchData = binding.searchTextPeminjaman
        //search data
        searchData.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }
        })
    }

    private fun setupAdapter(recyclerView: RecyclerView) {
        adapter.adapterCallback(adapterCallback)
            .setLayout(R.layout.pinjam_recyclerview)
            .filterable()
            .isVerticalView()
            .build(recyclerView)
    }

    private val adapterCallback = object : AdapterCallback<DataPeminjaman> {
        override fun initComponent(itemView: View, data: DataPeminjaman, itemIndex: Int) {
            // init utils
            itemView.tv_nama_peminjaman.text = data.nama_peminjam
            itemView.tv_jumlah_peminjaman.text = data.jumlah_barang.toString()
            itemView.tv_nim_peminjaman.text = data.nim_peminjam
            itemView.tv_kelas_peminjaman.text = data.kelas_peminjam
            itemView.tv_barang_peminjaman.text = data.pilihan_barang
            itemView.tv_waktu_peminjaman.text = data.waktu_peminjaman
            itemView.tv_waktu_pengembalian.text = data.waktu_pengembalian
        }

        // fungsi ini dipakai kalau kita click 1 layout recyclerviewnya
        override fun onItemClicked(itemView: View, data: DataPeminjaman, itemIndex: Int) {

        }
    }
}
