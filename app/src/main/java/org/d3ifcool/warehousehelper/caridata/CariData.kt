package org.d3ifcool.warehousehelper.caridata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.ActivityCariDataBinding
import org.d3ifcool.warehousehelper.tambahdata.Data

class CariData : AppCompatActivity() {
    private lateinit var binding: ActivityCariDataBinding
    lateinit var ref: DatabaseReference
    lateinit var dataList: MutableList<Data>
    lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cari_data)

        //toolbar
        val actionbar = supportActionBar
        actionbar!!.title = "Cari Barang"
        actionbar.setDisplayHomeAsUpEnabled(true)

        //funsgi read data
        ref = FirebaseDatabase.getInstance().getReference("WarehouseHelper")
        dataList = mutableListOf()
        listView = findViewById(R.id.list_data)


        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    dataList.clear()
                    for (h in p0.children) {
                        val data = h.getValue(Data::class.java)
                        dataList.add(data!!)
                    }
                    val adapter = CariAdapter(this@CariData, R.layout.list_data_cari, dataList)
                    listView.adapter = adapter
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
