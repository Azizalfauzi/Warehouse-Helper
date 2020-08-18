package org.d3ifcool.warehousehelper.autentifikasi

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_account_confirm.*
import kotlinx.android.synthetic.main.fragment_profile.*

import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.databinding.FragmentAccountConfirmBinding
import org.d3ifcool.warehousehelper.ui.DashboardActivity
import java.io.ByteArrayOutputStream

/**
 * A simple [Fragment] subclass.
 */
class AccountConfirm : Fragment() {
    private lateinit var binding: FragmentAccountConfirmBinding
    private lateinit var auth: FirebaseAuth
    private var currentUser = FirebaseAuth.getInstance().currentUser

    private var DEAULT_IMAGE_URL = "https://picsum.photos/200"
    private val REQUEST_IMAGE_CAPTURE = 100
    private lateinit var imageUri: Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_account_confirm, container, false)

        val user = auth.currentUser
        user?.let {
            val email = user.email
            binding.tvConfirmEmail.text = email.toString()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageConfirm.setOnClickListener {
            takePictureIntent()
        }

        binding.btAccAccount.setOnClickListener {
            val name = username.text.toString().trim()
            if (name.isEmpty()) {
                username.error = "Username Tidak Boleh Kosong"
                username.requestFocus()
                return@setOnClickListener
            } else {
                //current user
                val photo = when {
                    ::imageUri.isInitialized -> imageUri
                    currentUser?.photoUrl == null -> Uri.parse(DEAULT_IMAGE_URL)
                    else -> currentUser!!.photoUrl
                }
                val update = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .setPhotoUri(photo)
                    .build()
                currentUser?.updateProfile(update)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(requireContext(), DashboardActivity::class.java))
                        Toast.makeText(requireContext(), "Account is created", Toast.LENGTH_SHORT).show()
                    } else {
                        context?.toast(task.exception?.message!!)
                    }
                }
            }
        }
    }

    private fun takePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { pictureIntent ->
            pictureIntent.resolveActivity(activity?.packageManager!!)?.also {
                startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            uploadImageAndSaveUri(imageBitmap)
        }
    }

    private fun uploadImageAndSaveUri(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val storageRef =
            FirebaseStorage.getInstance().reference.child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        val upload = storageRef.putBytes(image)
        binding.progressConfirm.visibility = View.VISIBLE
        upload.addOnCompleteListener { uploadTask ->
            progress_confirm.visibility = View.INVISIBLE
            if (uploadTask.isSuccessful) {
                storageRef.downloadUrl.addOnCompleteListener { urlTask ->
                    urlTask.result?.let {
                        imageUri = it
                        activity?.toast(imageUri.toString())
                        image_confirm.setImageBitmap(bitmap)
                    }
                }
            } else {
                uploadTask.exception?.let {
                    activity?.toast(it.message!!)
                }
            }
        }
    }
}
