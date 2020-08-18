package org.d3ifcool.warehousehelper.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.android.synthetic.main.fragment_update_password.*

import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.autentifikasi.toast
import org.d3ifcool.warehousehelper.databinding.FragmentUpdatePasswordBinding

/**
 * A simple [Fragment] subclass.
 */
class UpdatePasswordFragment : Fragment() {
    private lateinit var binding: FragmentUpdatePasswordBinding
    private val currentUser = FirebaseAuth.getInstance().currentUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_update_password, container, false)
        binding.btBackPass.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_updatePasswordFragment_to_profileFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        konfirmasi_password.visibility = View.VISIBLE
        save_password.visibility = View.GONE

        bt_konfirmasi_password.setOnClickListener {
            val password = inp_konfirmasi_pass.text.toString().trim()

            if (password.isEmpty()) {
                inp_konfirmasi_pass.error = "Inputan Tidak Boleh Kosong!"
                inp_konfirmasi_pass.requestFocus()
                return@setOnClickListener
            }

            currentUser?.let { user ->
                val credential = EmailAuthProvider.getCredential(user.email!!, password)
                progressbar_konfirmasi.visibility = View.VISIBLE
                t_wait_konfirmasi.visibility = View.VISIBLE
                konfirmasi_password.visibility = View.GONE
                user.reauthenticate(credential)
                    .addOnCompleteListener { task ->
                        progressbar_konfirmasi.visibility = View.GONE
                        when {
                            task.isSuccessful -> {
                                konfirmasi_password.visibility = View.GONE
                                save_password.visibility = View.VISIBLE
                            }
                            task.exception is FirebaseAuthInvalidCredentialsException -> {
                                inp_konfirmasi_pass.error = "Invalid Password"
                                inp_konfirmasi_pass.requestFocus()
                            }
                            else -> context?.toast(task.exception?.message!!)
                        }
                    }
            }
        }

        bt_save_new_pass.setOnClickListener {
            val new_pass = inp_edit_pass.text.toString().trim()
            if (new_pass.isEmpty() || new_pass.length < 6) {
                inp_edit_pass.error = " at least  6 char password required"
                inp_edit_pass.requestFocus()
                return@setOnClickListener
            }
            if (new_pass != inp_edit_confirm_pass.text.toString().trim()) {
                inp_edit_pass.error = "password not match!"
                inp_edit_pass.requestFocus()
                return@setOnClickListener
            }

            currentUser?.let { user ->
                progressbar_save_password.visibility = View.VISIBLE
                t_wait_save_password.visibility = View.VISIBLE
                save_password.visibility = View.GONE
                user.updatePassword(new_pass)
                    .addOnCompleteListener { task ->
                        progressbar_save_password.visibility = View.GONE
                        t_wait_save_password.visibility = View.GONE
                        if (task.isSuccessful) {
                            view.findNavController()
                                .navigate(R.id.action_updatePasswordFragment_to_profileFragment)
                        } else {
                            context?.toast(task.exception?.message!!)
                        }
                    }
            }
        }
    }
}
