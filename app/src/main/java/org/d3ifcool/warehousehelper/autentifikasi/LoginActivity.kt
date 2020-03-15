package org.d3ifcool.warehousehelper.autentifikasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.d3ifcool.warehousehelper.DashboardActivity
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        binding.btRegLogin.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.btnlogin.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {
        if (inp_email_reg.text.toString().isEmpty()) {
            inp_email_reg.error = "plase enter email"
            inp_email_reg.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(inp_email_reg.text.toString()).matches()) {
            inp_email_reg.error = "plase enter email"
            inp_email_reg.requestFocus()
            return
        }
        if (inp_pass_login.text.toString().isEmpty()) {
            inp_pass_login.error = "please enter password"
            inp_pass_login.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(
            inp_email_login.text.toString(),
            inp_pass_login.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                    finish()
                } else {
                    Toast.makeText(
                        baseContext, "Password / Email anda salah",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, DashboardActivity::class.java))
            } else {
                Toast.makeText(
                    baseContext, "Lakukan verifikasi email anda",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                baseContext, "Lakukan Login Terlebih dahulu",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
