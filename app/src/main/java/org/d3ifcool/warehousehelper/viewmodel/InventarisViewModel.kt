package org.d3ifcool.warehousehelper.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import org.d3ifcool.warehousehelper.model.DataInventaris
import org.d3ifcool.warehousehelper.model.DataKeluarBarang
import org.d3ifcool.warehousehelper.utils.DATA_INVENTARIS
import org.d3ifcool.warehousehelper.utils.DATA_KELUAR

class InventarisViewModel : ViewModel() {

    //make database
    private val dbInventaris = FirebaseDatabase.getInstance().getReference(DATA_INVENTARIS)

    //make database keluar barang
    private val dbKeluarBarang = FirebaseDatabase.getInstance().getReference(DATA_KELUAR)

    //buat list inventaris
    private val _inventarisData = MutableLiveData<List<DataInventaris>>()
    val inventarisData: LiveData<List<DataInventaris>>
        get() = _inventarisData

    //buat single inventaris
    private val _inventaris = MutableLiveData<DataInventaris>()
    val inventaris: LiveData<DataInventaris>
        get() = _inventaris

    //create result
    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result
    //response
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response


    //tambah data ke realtime database
    fun addInventaris(dataInventaris: DataInventaris) {
        dataInventaris.id = dbInventaris.push().key
        dbInventaris.child(dataInventaris.id!!).setValue(dataInventaris).addOnCompleteListener {
            if (it.isSuccessful) {
                _result.value = null
            } else {
                _result.value = it.exception
            }
        }
    }

    fun addKeluarBarang(dataKeluarBarang: DataKeluarBarang) {
        dataKeluarBarang.id = dbKeluarBarang.push().key
        dbKeluarBarang.child(dataKeluarBarang.id!!).setValue(dataKeluarBarang)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    //buat perubahan data untuk realtime update
    private val childEventListener = object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {}

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

        //update data otomatis ketika di edit
        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            val dataInventaris = p0.getValue(DataInventaris::class.java)
            dataInventaris?.id = p0.key
            _inventaris.value = dataInventaris
        }

        //update data otomatis ketika data ditambahkan
        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            val dataInventaris = p0.getValue(DataInventaris::class.java)
            dataInventaris?.id = p0.key
            _inventaris.value = dataInventaris
        }

        override fun onChildRemoved(p0: DataSnapshot) {
            val dataInventaris = p0.getValue(DataInventaris::class.java)
            dataInventaris?.id = p0.key
            dataInventaris?.isDelete = true
            _inventaris.value = dataInventaris
        }
    }

    //fungsi get data realtime update
    fun getRealtimeUpdate() {
        dbInventaris.addChildEventListener(childEventListener)
    }

    //buat event menampilkan data firebase dengan metode fetch
    private val valueEventListener = object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {}

        override fun onDataChange(p0: DataSnapshot) {
            if (p0.exists()) {
                val inventarisData = mutableListOf<DataInventaris>()
                for (dataInventarisSnapshot in p0.children) {
                    val dataInventaris = dataInventarisSnapshot.getValue(DataInventaris::class.java)
                    dataInventaris?.id = dataInventarisSnapshot.key
                    dataInventaris?.let { inventarisData.add(it) }
                }
                _inventarisData.value = inventarisData
            }
        }
    }

    //fetch untuk menampilkan data dari firebase
    fun fetchInventarisData() {
        dbInventaris.addListenerForSingleValueEvent(valueEventListener)
    }

    //fungsi update
    fun updateInventaris(dataInventaris: DataInventaris) {
        dbInventaris.child(dataInventaris.id!!).setValue(dataInventaris).addOnCompleteListener {
            if (it.isSuccessful) {
                _result.value = null

            } else {
                _result.value = it.exception
            }
        }
    }

    //fungsi delete
    fun deleteInventaris(dataInventaris: DataInventaris) {
        dbInventaris.child(dataInventaris.id!!).setValue(null).addOnCompleteListener {
            if (it.isSuccessful) {
                _result.value = null
                _response.value = "Berhasil Menghapus Data!"
            } else {
                _result.value = it.exception
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        dbInventaris.removeEventListener(childEventListener)
    }
}