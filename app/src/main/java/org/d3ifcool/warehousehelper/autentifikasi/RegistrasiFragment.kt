package org.d3ifcool.warehousehelper.autentifikasi


import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_registrasi.*

import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.FragmentRegistrasiBinding

/**
 * A simple [Fragment] subclass.
 */
class RegistrasiFragment : Fragment() {
    private lateinit var binding: FragmentRegistrasiBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registrasi, container, false)
        //inisialiasi fungsi
        auth = FirebaseAuth.getInstance()
        //button registrasi
        binding.btReg.setOnClickListener {
            val email = email_reg.text.toString().trim()
            val pass = password_reg.text.toString()
            if (email.isEmpty()) {
                email_reg.error = "Inputan email tidak boleh kosong!"
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                email_reg.error = "Email Tidak Valid"
                email_reg.requestFocus()
                return@setOnClickListener
            }
            if (pass.isEmpty() || pass.length < 6) {
                password_reg.error = "6 Char password required"
                password_reg.requestFocus()
                return@setOnClickListener
            }
            if (pass != password_reg_confirm.text.toString().trim()) {
                password_reg_confirm.error = "Password not match!"
                password_reg_confirm.requestFocus()
                return@setOnClickListener
            }
            signUpUser(email, pass)
        }
        binding.imgBackReg.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
        return binding.root
    }



    private fun signUpUser(email: String, pass: String) {
        //panggil proggressbar untuk loading
        progress_reg.visibility = View.VISIBLE
        //method membuat user baru menggunakan email dan password
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(requireActivity()) { task ->
                //ketika berhasil
                progress_reg.visibility = View.VISIBLE
                if (task.isSuccessful) {
                    progress_reg.visibility = View.GONE
                    startActivity(Intent(requireContext(), ConfirmAccount::class.java))
                } else {
                    Toast.makeText(requireContext(), "Gagal menjalankan aksi", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    //jalan kan fungsi semua (mulai)
    override fun onStart() {
        super.onStart()
    }
}
