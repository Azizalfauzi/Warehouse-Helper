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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_dialog_kurang_barang.*

import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.FragmentDialogKurangBarangBinding
import org.d3ifcool.warehousehelper.model.DataInventaris
import org.d3ifcool.warehousehelper.model.DataKeluarBarang
import org.d3ifcool.warehousehelper.ui.activity.KeluarBarang
import org.d3ifcool.warehousehelper.viewmodel.InventarisViewModel

/**
 * A simple [Fragment] subclass.
 */
class DialogKurangBarang(private val inventaris: DataInventaris) : DialogFragment() {
    private lateinit var binding: FragmentDialogKurangBarangBinding
    private lateinit var viewModel: InventarisViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dialog_kurang_barang,
            container,
            false
        )
        viewModel = ViewModelProviders.of(this).get(InventarisViewModel::class.java)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //style pop up
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Light_Dialog_MinWidth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_jumlah_dialog_kurang.text = inventaris.jumlah_barang.toString()
        tv_nama_dialog_kurang.text = inventaris.nama_barang

        var nilai_total = 0
        bt_minus_dialog.setOnClickListener {
            nilai_total -= 1
            inp_kurang_dialog.text = nilai_total.toString()
        }

        bt_plus_dialog.setOnClickListener {
            nilai_total += 1
            inp_kurang_dialog.text = nilai_total.toString()
        }

        bt_keluar_barang_dialog.setOnClickListener {
            if (nilai_total == 0) {
                Toast.makeText(requireContext(), "Tidak Boleh Nol", Toast.LENGTH_SHORT).show()
            } else if (nilai_total <= 0) {
                Toast.makeText(requireContext(), "Tidak Boleh Bernilai Negatif", Toast.LENGTH_SHORT)
                    .show()
            } else {
                var total = 0
                val jumlah_barang = inventaris.jumlah_barang.toString().toInt()
                if (jumlah_barang < nilai_total) {
                    Toast.makeText(
                        requireContext(),
                        "Jumlah Stok Tidak Mencukupi",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    total = jumlah_barang - nilai_total
                    if (total == 0) {
                        viewModel.deleteInventaris(inventaris)
                        startActivity(Intent(requireContext(), KeluarBarang::class.java))
                        Toast.makeText(requireContext(), "Stok Telah Habis", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        //update stok
                        val jumlah_barang = total.toString().toInt()

                        inventaris.jumlah_barang = jumlah_barang

                        viewModel.updateInventaris(inventaris)
                        //add riwayat keluar barang
                        val dataKeluar = DataKeluarBarang()
                        val jumlah_keluar = total.toString().toInt()
                        val nama_keluar =inventaris.nama_barang
                        dataKeluar.jumlah_barang = jumlah_keluar
                        dataKeluar.nama_barang = nama_keluar

                        viewModel.addKeluarBarang(dataKeluar)

                        startActivity(Intent(requireContext(), KeluarBarang::class.java))
                        Toast.makeText(
                            requireContext(),
                            "Congrats Barang Telah Berkurang $total",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
    }
}
