package org.d3ifcool.warehousehelper.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.autentifikasi.logout
import org.d3ifcool.warehousehelper.databinding.ActivitySettingsBinding
import org.d3ifcool.warehousehelper.ui.DashboardActivity

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val currentUser = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        //current user
        currentUser?.let { user ->
            Glide.with(this)
                .load(user.photoUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(img_settings)
            user_settings.text = user.displayName
            email_settings.text = user.email
        }
        binding.tProfileSetting.setOnClickListener{
            startActivity(Intent(this,ProfileActivity::class.java))
        }
        binding.tLogoutSetting.setOnClickListener {
           signOut()
        }
        binding.btBackSettings.setOnClickListener {
            startActivity(Intent(this,DashboardActivity::class.java))
        }
    }
    //fungsi auth logout
    private fun signOut() {
        // fungsi alert dialog
        AlertDialog.Builder(this).apply {
            //set title
            setTitle("Are you sure logout?")
            //fungsi ketika di klik yes
            setPositiveButton("Yes") { _, _ ->
                //fungsi firebase auth
                FirebaseAuth.getInstance().signOut()
                logout()
                finish()
                finishAffinity()
            }
            //fungsi ketika di klik tidak
            setNegativeButton("No") { _, _ ->

            }
        } //panggil dan tampilkan fungsi ini
            .create().show()
    }
}
