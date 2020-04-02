package org.d3ifcool.warehousehelper.Dashboard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import org.d3ifcool.warehousehelper.AboutActivity
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.tambahdata.TambahData
import org.d3ifcool.warehousehelper.autentifikasi.LoginActivity
import org.d3ifcool.warehousehelper.inventaris.CariData
import org.d3ifcool.warehousehelper.databinding.ActivityDashboardBinding


class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        supportActionBar?.title = "Warehouse Helper"
        binding.btTambahBarang.setOnClickListener {
            startActivity(Intent(this,
                TambahData::class.java))
        }
        binding.btCariBarang.setOnClickListener {
            startActivity(Intent(this,CariData::class.java))
        }
        binding.btPinjamBarang.setOnClickListener {

        }
    }


        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> startActivity(Intent(this, AboutActivity::class.java))
        }
        if (item.itemId == R.id.logout) {
            signOut()
            finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }


     fun signOut() {
        // [START auth_sign_out]
        startActivity(Intent(this, LoginActivity::class.java))
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(this, "Berhasil Logout", Toast.LENGTH_SHORT).show()
        finish()
    }
}
