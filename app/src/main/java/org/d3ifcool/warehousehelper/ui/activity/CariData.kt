package org.d3ifcool.warehousehelper.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_cari_data.*
import kotlinx.android.synthetic.main.list_data_cari_find.view.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.adapter.CariAdapter
import org.d3ifcool.warehousehelper.databinding.ActivityCariDataBinding
import org.d3ifcool.warehousehelper.model.Data

class CariData : AppCompatActivity() {
    private lateinit var binding: ActivityCariDataBinding
    lateinit var ref: DatabaseReference
    lateinit var dataList: MutableList<Data>
    lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cari_data)

//        fungsi read data
        binding.btCariData.setOnClickListener {
            if (inp_cari_data.text.isEmpty()){
                Toast.makeText(applicationContext,"Inputan tidak boleh kosong!",Toast.LENGTH_SHORT).show()
            }else{
                cariSingleData()
            }
        }


        //toolbar
        val actionbar = supportActionBar
        actionbar!!.title = "Inventaris Barang"
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
                    val adapter =
                        CariAdapter(
                            this@CariData,
                            R.layout.list_data_cari,
                            dataList
                        )
                    listView.adapter = adapter
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    fun cariSingleData() {
        //inflate the dialog
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.list_data_cari_find, null)
        //alert dialog builder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Data Found!")
        //show dialog
        val mAlertDialog = mBuilder.show()

        //dissmiss dialog


        val nama_barang = mDialogView.findViewById<TextView>(R.id.tv_hasil_nama_find)
        val jumlah_barang = mDialogView.findViewById<TextView>(R.id.tv_hasil_jumlah_find)
        val harga_barang = mDialogView.findViewById<TextView>(R.id.tv_hasil_harga_find)
        val tanggal_masuk = mDialogView.findViewById<TextView>(R.id.tv_hasil_tanggal_find)

        FirebaseDatabase.getInstance().reference
            .child("WarehouseHelper")
            .orderByChild("nama_barang").equalTo(inp_cari_data.text.toString().trim())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var map = p0.children.first().value as Map<String, Int>
                    nama_barang.text = map["nama_barang"].toString()
                    jumlah_barang.text = map["jumlah_barang"].toString()
                    harga_barang.text = map["hargaBarang"].toString()
                    tanggal_masuk.text = map["tanggal_masuk"].toString()
                }
            })
        mDialogView.bt_back_find.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }
}
