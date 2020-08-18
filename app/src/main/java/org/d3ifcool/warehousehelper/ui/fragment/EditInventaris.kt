package org.d3ifcool.warehousehelper.ui.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_tambah_data.*
import kotlinx.android.synthetic.main.fragment_edit_inventaris.*
import kotlinx.android.synthetic.main.fragment_edit_inventaris.inp_jumlah_barang_edit
import kotlinx.android.synthetic.main.fragment_edit_inventaris.inp_nama_barang_edit

import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.FragmentEditInventarisBinding
import org.d3ifcool.warehousehelper.model.DataInventaris
import org.d3ifcool.warehousehelper.ui.activity.InventarisBarang
import org.d3ifcool.warehousehelper.viewmodel.InventarisViewModel

/**
 * A simple [Fragment] subclass.
 */
class EditInventaris(private val inventaris: DataInventaris) : DialogFragment() {
    private lateinit var binding: FragmentEditInventarisBinding
    private lateinit var viewModel: InventarisViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this).get(InventarisViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_inventaris, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //styling pop up
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Light_Dialog_MinWidth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        inp_nama_barang_edit.setText(inventaris.nama_barang)
        inp_jumlah_barang_edit.setText(inventaris.jumlah_barang.toString())
        inp_harga_barang_edit.setText(inventaris.harga_barang.toString())
        tv_tanggal_edit.text = inventaris.tanggal_masuk

        bt_update.setOnClickListener {
            val nama_barang = inp_nama_barang_edit.text.toString().trim()
            val jumlah_barang = inp_jumlah_barang_edit.text.toString().trim()
            val harga_brang = inp_harga_barang_edit.text.toString().trim()
            if (nama_barang.isEmpty()) {
                inp_nama_barang_edit.error = "Inputan Nama Barang Tidak Boleh Kosong!"
                return@setOnClickListener
            } else if (jumlah_barang.isEmpty()) {
                inp_jumlah_barang_edit.error = "Inputan Jumlah Barang Tidak Boleh Kosong!"
                return@setOnClickListener
            } else if (harga_brang.isEmpty()) {
                inp_harga_barang_edit.error = "Inputan Harga Barang Tidak Boleh Kosong!"
                return@setOnClickListener
            } else {
                val nama_brang_edit = inp_nama_barang_edit.text.toString()
                val jumlah_barang_edit = inp_jumlah_barang_edit.text.toString().toInt()
                val harga_barang_edit = inp_harga_barang_edit.text.toString().toInt()
                val tanggal_barang_edit = tv_tanggal_edit.text.toString()

                inventaris.nama_barang = nama_brang_edit
                inventaris.jumlah_barang = jumlah_barang_edit
                inventaris.harga_barang = harga_barang_edit
                inventaris.tanggal_masuk = tanggal_barang_edit

                viewModel.updateInventaris(inventaris)
                startActivity(Intent(requireContext(), InventarisBarang::class.java))
            }
        }
    }
}
