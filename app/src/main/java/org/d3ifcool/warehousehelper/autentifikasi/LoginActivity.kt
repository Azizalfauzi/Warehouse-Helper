package org.d3ifcool.warehousehelper.autentifikasi


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import org.d3ifcool.warehousehelper.ui.DashboardActivity
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    //variable global untuk mengakses auth firebase
    private lateinit var auth: FirebaseAuth

    //variable global untuk untuk memanggil layar menggunakan binding
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inisialisasikan untuk firebase auth
        auth = FirebaseAuth.getInstance()
        //panggil layar menggunakan binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        //button berpindah ke register
        binding.btRegLogin.setOnClickListener {
            //fungsi berpindah ke register activity
            startActivity(Intent(this, RegistrasiActivity::class.java))
        }
        //button login ketika di klik
        binding.btnLogin.setOnClickListener {
            //inisialisasikan variable inputan
            val email = inp_email_login.text.toString().trim()
            val pass = inp_pass_login.text.toString().trim()
            //lakukan pengecekan inputan email apakah kosong
            if (email.isEmpty()) {
                inp_email_login.error = "plase enter email"
                inp_email_login.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(inp_email_login.text.toString()).matches()) {
                inp_email_login.error = "plase enter email"
                inp_email_login.requestFocus()
                return@setOnClickListener
            }
            //lakukan pengecekan inputan apakah password kosong
            if (pass.isEmpty()) {
                inp_pass_login.error = "please enter password"
                inp_pass_login.requestFocus()
                return@setOnClickListener
            }
            //fungsi login menggunakan 2 param
            doLogin(email, pass)
        }
        //button berpindah ke lupas password
        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }
    }

    //fungsi login
    private fun doLogin(email: String, pass: String) {
        //panggil proggresbar untuk aktif
        progressBar.visibility = View.VISIBLE
        //panggil auth untuk koneksi menggunakan method di bawah ini
        auth.signInWithEmailAndPassword(email, pass)
            //setelah berhasil kerjakan fungsi ini
            .addOnCompleteListener(this) { task ->
                //panggil proggres bar dan hilangkan karena berhasil
                progressBar.visibility = View.GONE
                //setelah berhasil
                if (task.isSuccessful) {
                    //panggil herlper untuk berpindah ke halaman dashboard
                    login()
                } else {
                    //jika gagal panggil exception dan tampilkan pesan kegagalan
                    task.exception?.message?.let {
                        toast(it)
//                        tv_forgot_password.visibility = View.VISIBLE
                    }
                }
            }
    }

    //jalan kan fungsi yang diatas
    public override fun onStart() {
        super.onStart()
        auth.currentUser?.let {
            login()
        }
    }
}
