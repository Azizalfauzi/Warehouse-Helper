package org.d3ifcool.warehousehelper.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.adapter.DataRiwayatPeminjaman
import org.d3ifcool.warehousehelper.databinding.ActivityRiwayatPeminjamanBinding
import org.d3ifcool.warehousehelper.model.DataPeminjaman

class RiwayatPeminjaman : AppCompatActivity() {
    private lateinit var binding: ActivityRiwayatPeminjamanBinding
    lateinit var ref: DatabaseReference
    lateinit var listView: ListView
    lateinit var dataList: MutableList<DataPeminjaman>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_riwayat_peminjaman)

        //read data from database
        readDataDatabase()
        //toolbar
        //toolbar
        val actionbar = supportActionBar
        actionbar!!.title = "Riwayat Peminjaman"
        actionbar.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun readDataDatabase() {
        ref = FirebaseDatabase.getInstance().getReference("PeminjamanBarang")
        dataList = mutableListOf()
        listView = findViewById(R.id.list_data_riwayat)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    dataList.clear()
                    for (h in p0.children) {
                        val data = h.getValue(DataPeminjaman::class.java)
                        dataList.add(data!!)
                    }
                    val adapter =
                        DataRiwayatPeminjaman(
                            this@RiwayatPeminjaman,
                            R.layout.list_data_riwayat_peminjaman,
                            dataList
                        )
                    listView.adapter = adapter
                }
            }
        })
    }
}
