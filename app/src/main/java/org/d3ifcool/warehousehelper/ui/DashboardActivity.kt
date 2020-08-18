package org.d3ifcool.warehousehelper.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.item_dashboard.view.*
import kotlinx.android.synthetic.main.pinjam_recyclerview.view.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.autentifikasi.LoginActivity
import org.d3ifcool.warehousehelper.autentifikasi.logout
import org.d3ifcool.warehousehelper.databinding.ActivityDashboardBinding
import org.d3ifcool.warehousehelper.model.DataInventaris
import org.d3ifcool.warehousehelper.model.DataKeluarBarang
import org.d3ifcool.warehousehelper.model.DataPeminjaman
import org.d3ifcool.warehousehelper.ui.activity.*
import org.d3ifcool.warehousehelper.utils.DATA_INVENTARIS
import org.d3ifcool.warehousehelper.viewmodel.InventarisViewModel
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

//halaman dashboard setelah sukses melakukan login dan register
class DashboardActivity : AppCompatActivity() {
    //membuat variable global untuk memanggil layar menggunkan binding
    private lateinit var binding: ActivityDashboardBinding

    //membuat varible global untuk koneksi auth firebase
    private lateinit var auth: FirebaseAuth

    //view model stock barang
    private lateinit var viewModel: InventarisViewModel

    //panggil kelas reusable adapter
    private lateinit var adapter: ReusableAdapter<DataInventaris>

    //current user var
    private val currentUser = FirebaseAuth.getInstance().currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //memanggil layar menggunakan binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        //menu profile
        currentUser?.let { user ->
            Glide.with(this)
                .load(user.photoUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(image_menu)
        }
        binding.imageMenu.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        //tombol untuk berpindah ke layar tambah barang menggunakan binding
        binding.btTambahBarang.setOnClickListener {
            //fungsi untuk berpindah ke activity
            startActivity(Intent(this, TambahData::class.java))
        }
//        tombol untuk berpindah ke layar inventaris data menggunkaan binding
        binding.btInventarisBarang.setOnClickListener {
            startActivity(Intent(this, InventarisBarang::class.java))
        }
        //tombol untuk berpindah ke layar pinjam barang menggunakan binding
        binding.btPinjamBarang.setOnClickListener {
            startActivity(Intent(this, Pilihan_Barang_Peminjaman::class.java))
        }
        //tombol untuk berpindah ke layar riwayat peminjaman menggunkanan binding
        binding.btRiwayatPeminjaman.setOnClickListener {
            startActivity(Intent(this, RiwayatPeminjaman::class.java))
        }
        //tombol untuk berpindah ke layar kurang barang menggunkan binding
        binding.btKurangBarang.setOnClickListener {
            startActivity(Intent(this, KeluarBarang::class.java))
        }
        //fungsi spinner untuk menu
//        optionSelected()
        //get user profile
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        user?.let {
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
            binding.tUser.text = name.toString()
        }
        //panggil view model
        viewModel = ViewModelProvider(this).get(InventarisViewModel::class.java)
        //fetch data
        //panggil fungsi viewmodel fetch data
        viewModel.fetchInventarisData()

        //panggil fungsi get real time database
        viewModel.getRealtimeUpdate()

        //init adapter
        adapter = ReusableAdapter(this)
        //setup adapter
        setupAdapter(binding.rvDashboard)
        //setup reusable adapter
        viewModel.inventarisData.observe({ lifecycle }, {
            adapter.addData(it)
        })
        //set observe untuk adapter list dia sendiri
        viewModel.inventaris.observe({ lifecycle }, {
            adapter.updateData(it)
        })
    }

    private fun setupAdapter(recyclerView: RecyclerView) {
        adapter.adapterCallback(adapterCallback)
            .setLayout(R.layout.item_dashboard)
            .filterable()
            .isHorizontalView()
            .build(recyclerView)
    }

    private val adapterCallback = object : AdapterCallback<DataInventaris> {
        override fun initComponent(itemView: View, data: DataInventaris, itemIndex: Int) {
            // init utils
            itemView.tv_nama_dashboard.text = data.nama_barang
            itemView.tv_stock_dashboard.text = data.jumlah_barang.toString()
        }

        // fungsi ini dipakai kalau kita click 1 layout recyclerviewnya
        override fun onItemClicked(itemView: View, data: DataInventaris, itemIndex: Int) {
        }
    }

    //fungsi spinner didalam dashboard
//    private fun optionSelected() {
//        val option = binding.spinnerMenu
//
//        val options =
//            arrayOf("Home", "Profile", "About", "Logout")
//
//        option.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)
//
//        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                if (options.get(position) == "Logout") {
//                    signOut()
//                }
//                if (options.get(position) == "Profile") {
//                    intentProfile()
//                }
//            }
//        }
//    }

    //fungsi untuk berpindah kehalaman profile menggunakan spinner
//    fun intentProfile() {
//        startActivity(Intent(this, SettingsActivity::class.java))
//    }
//
//    //fungsi auth logout
//    fun signOut() {
//        // fungsi alert dialog
//        AlertDialog.Builder(this).apply {
//            //set title
//            setTitle("Are you sure logout?")
//            //fungsi ketika di klik yes
//            setPositiveButton("Yes") { _, _ ->
//                //fungsi firebase auth
//                FirebaseAuth.getInstance().signOut()
//                logout()
//                finish()
//                finishAffinity()
//            }
//            //fungsi ketika di klik tidak
//            setNegativeButton("No") { _, _ ->
//
//            }
//        } //panggil dan tampilkan fungsi ini
//            .create().show()
//    }
}
