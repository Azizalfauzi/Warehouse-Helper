package org.d3ifcool.warehousehelper.autentifikasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivitySignUpBinding>(this, R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
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
        if (inp_pass_reg.text.toString().isEmpty()) {
            inp_pass_reg.error = "please enter password"
            inp_pass_reg.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(
            inp_email_reg.text.toString(),
            inp_pass_reg.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                        }
                } else {
                    Toast.makeText(
                        baseContext, "Sign Up failed.and try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
