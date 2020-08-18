package org.d3ifcool.warehousehelper.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_username.*

import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.autentifikasi.toast
import org.d3ifcool.warehousehelper.databinding.FragmentUsernameBinding

/**
 * A simple [Fragment] subclass.
 */
class UsernameFragment : Fragment() {
    private lateinit var binding: FragmentUsernameBinding
    private val currentUser = FirebaseAuth.getInstance().currentUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_username, container, false)
        binding.backEditUsername.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_usernameFragment_to_profileFragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        currentUser?.let { user ->
            edit_username.setText(user.displayName)
        }
        binding.btEditUsername.setOnClickListener {
            val username = edit_username.text.toString().trim()
            if (username.isEmpty()) {
                edit_username.error = "Username tidak boleh kosong!"
                return@setOnClickListener
            } else {
                val update = UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build()
                username_progressbar.visibility = View.VISIBLE
                currentUser?.updateProfile(update)?.addOnCompleteListener { task ->
                    username_progressbar.visibility = View.INVISIBLE
                    if (task.isSuccessful) {
                        context?.toast("Username Update")
                        view?.findNavController()
                            ?.navigate(R.id.action_usernameFragment_to_profileFragment)
                    } else {
                        context?.toast(task.exception?.message!!)
                    }
                }

            }
        }
    }
}
