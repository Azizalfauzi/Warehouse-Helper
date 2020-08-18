package org.d3ifcool.warehousehelper.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_phone.*

import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.autentifikasi.toast
import org.d3ifcool.warehousehelper.databinding.FragmentPhoneBinding
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class PhoneFragment : Fragment() {
    private lateinit var binding: FragmentPhoneBinding
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private var verificationId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_phone, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.btPhoneEdit.setOnClickListener {
//            val phone = phone_edit.text.toString().trim()
//            if (phone.isEmpty() || phone.length != 10) {
//                phone_edit.error = "No Telephone tidak boleh kosong"
//                phone_edit.requestFocus()
//                return@setOnClickListener
//            } else {
//                val phoneNumber = '+' + ccp.selectedCountryCode + phone
//                PhoneAuthProvider.getInstance()
//                    .verifyPhoneNumber(
//                        phoneNumber,
//                        60,
//                        TimeUnit.SECONDS,
//                        requireActivity(),
//                        phoneAuthCallback
//                    )
//                progressbar_phone.visibility = View.VISIBLE
//                verification.visibility = View.GONE
//                save_verification.visibility = View.VISIBLE
//            }
//        }
//        binding.btSavePhone.setOnClickListener {
//            val code = phone_save.text.toString().trim()
//            if (code.isEmpty()) {
//                phone_save.error = "Phone required!"
//                phone_save.requestFocus()
//                return@setOnClickListener
//            } else {
//                verificationId?.let {
//                    val credential = PhoneAuthProvider.getCredential(it, code)
//                    addPhoneNumber(credential)
//                }
//            }
//        }
    }

//    private val phoneAuthCallback =
//        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
//                phoneAuthCredential?.let {
//                    addPhoneNumber(phoneAuthCredential)
//                }
//            }
//
//            override fun onVerificationFailed(exception: FirebaseException) {
//                context?.toast(exception?.message!!)
//            }
//
//            override fun onCodeSent(
//                verivicationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {
//                super.onCodeSent(verivicationId, token)
//                this@PhoneFragment.verificationId = verivicationId
//            }
//        }
//
//    private fun addPhoneNumber(phoneAuthCredential: PhoneAuthCredential) {
//        FirebaseAuth.getInstance()
//            .currentUser?.updatePhoneNumber(phoneAuthCredential)
//            ?.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    context?.toast("Phone Added")
//                    view?.findNavController()
//                        ?.navigate(R.id.action_phoneFragment_to_profileFragment)
//                } else {
//                    context?.toast(task.exception?.message!!)
//                }
//            }
//    }
}
