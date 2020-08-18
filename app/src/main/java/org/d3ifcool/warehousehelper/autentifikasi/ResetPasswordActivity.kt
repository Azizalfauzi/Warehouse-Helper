package org.d3ifcool.warehousehelper.autentifikasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View

import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_reset_password.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.ActivityResetPasswordBinding
import java.util.regex.Pattern


class ResetPasswordActivity : AppCompatActivity() {
    //variable global untuk memanggil layar menggunakan binding
    private lateinit var binding: ActivityResetPasswordBinding

    //variable global untuk koneksi auth firebase
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //panggil layar menggunakan binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password)
        //tombol lupa password
        bt_reset_password.setOnClickListener {
            //inisialisasi variable email
            val email_reset = inp_email_reset.text.toString().trim()
            //lakukan check inputan
            if (email_reset.isEmpty()) {
                inp_email_reset.error = "plase enter email!"
                inp_email_reset.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email_reset).matches()) {
                inp_email_reset.error = "plase enter email"
                inp_email_reset.requestFocus()
                return@setOnClickListener
            }
            //panggil proggress bar untuk loading
            proggres_reset.visibility = View.VISIBLE
            //panggil method firebase untuk mengganti password melalui verifikasi email
            FirebaseAuth.getInstance()
                .sendPasswordResetEmail(email_reset)
                .addOnCompleteListener { task ->
                    proggres_reset.visibility = View.GONE
                    if (task.isSuccessful) {
                        this.toast("Check Your Email")
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        this.toast(task.exception?.message!!)
                    }
                }
        }
    }
}
