package org.d3ifcool.warehousehelper.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import org.d3ifcool.warehousehelper.model.DataPeminjaman
import org.d3ifcool.warehousehelper.utils.DATA_PEMINJAMAN

class PeminjamanViewModel : ViewModel() {
    //buat database
    private val dbPeminjaman = FirebaseDatabase.getInstance().getReference(DATA_PEMINJAMAN)

    //buat list peminjaman
    private val _dataPeminjaman = MutableLiveData<List<DataPeminjaman>>()
    val dataPeminjaman: LiveData<List<DataPeminjaman>>
        get() = _dataPeminjaman

    //buat single dataPeminjaman
    private val _peminjaman = MutableLiveData<DataPeminjaman>()
    val peminjaman: LiveData<DataPeminjaman>
        get() = _peminjaman

    //buat result data
    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    //fungsi menambahkan data ke realtime database
    fun addPeminjaman(peminjaman: DataPeminjaman) {
        peminjaman.id = dbPeminjaman.push().key
        dbPeminjaman.child(peminjaman.id!!).setValue(peminjaman).addOnCompleteListener {
            if (it.isSuccessful) {
                _result.value = null
            } else {
                _result.value = it.exception
            }
        }
    }

    private val childEventListener = object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {}

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            val peminjaman = p0.getValue(DataPeminjaman::class.java)
            peminjaman?.id = p0.key
            _peminjaman.value = peminjaman
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            val peminjaman = p0.getValue(DataPeminjaman::class.java)
            peminjaman?.id = p0.key
            _peminjaman.value = peminjaman
        }

        override fun onChildRemoved(p0: DataSnapshot) {
            val peminjaman = p0.getValue(DataPeminjaman::class.java)
            peminjaman?.id = p0.key
            peminjaman?.isDelete = true
            _peminjaman.value = peminjaman
        }
    }

    fun getRealtimeDatabase() {
        dbPeminjaman.addChildEventListener(childEventListener)
    }

    private val valueEventListener = object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {}

        override fun onDataChange(p0: DataSnapshot) {
            if (p0.exists()) {
                val dataPeminjaman = mutableListOf<DataPeminjaman>()
                for (peminjamanSnapshot in p0.children) {
                    val peminjaman = peminjamanSnapshot.getValue(DataPeminjaman::class.java)
                    peminjaman?.id = peminjamanSnapshot.key
                    peminjaman?.let { dataPeminjaman.add(it) }
                }
                _dataPeminjaman.value = dataPeminjaman
            }
        }
    }

    fun fetchPeminjaman() {
        dbPeminjaman.addListenerForSingleValueEvent(valueEventListener)
    }


    //fungsi update
    fun updatePeminjaman(peminjaman: DataPeminjaman) {
        dbPeminjaman.child(peminjaman.id!!).setValue(peminjaman).addOnCompleteListener {
            if (it.isSuccessful) {
                _result.value = null
            } else {
                _result.value = it.exception
            }
        }
    }

    //fungsi delete
    fun deletePeminjaman(peminjaman: DataPeminjaman) {
        dbPeminjaman.child(peminjaman.id!!).setValue(null).addOnCompleteListener {
            if (it.isSuccessful) {
                _result.value = null
            } else {
                _result.value = it.exception
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        dbPeminjaman.removeEventListener(childEventListener)
    }
}