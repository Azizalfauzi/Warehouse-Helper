package org.d3ifcool.warehousehelper.ui.fragment

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
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_profile.*

import org.d3ifcool.warehousehelper.R
import org.d3ifcool.warehousehelper.autentifikasi.toast
import org.d3ifcool.warehousehelper.databinding.FragmentProfileBinding
import org.d3ifcool.warehousehelper.ui.DashboardActivity
import org.d3ifcool.warehousehelper.ui.activity.SettingsActivity
import java.io.ByteArrayOutputStream

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var imageUri: Uri
    private val REQUEST_IMAGE_CAPTURE = 100
    private var DEAULT_IMAGE_URL = "https://picsum.photos/200"
    private val currentUser = FirebaseAuth.getInstance().currentUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)


        binding.btBackProfile.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }
        binding.tUsername.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_profileFragment_to_usernameFragment)
        }
        binding.tPassword.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_profileFragment_to_updatePasswordFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentUser?.let { user ->
            Glide.with(this)
                .load(user.photoUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(image_view_profile)

            user_profile.text = user.displayName
            email_profile.text = user.email
        }
//        bt_save_profile.setOnClickListener {
//            val photo = when {
//                ::imageUri.isInitialized -> imageUri
//                currentUser?.photoUrl == null -> Uri.parse(DEAULT_IMAGE_URL)
//                else -> currentUser.photoUrl
//            }
//            val name = inp_user_profile.text.toString().trim()
//            if (name.isEmpty()) {
//                inp_user_profile.error = "Name is required!"
//                return@setOnClickListener
//            }
//            val update = UserProfileChangeRequest.Builder()
//                .setDisplayName(name)
//                .setPhotoUri(photo)
//                .build()
//            progress_bar_save_change.visibility = View.VISIBLE
//
//            currentUser?.updateProfile(update)?.addOnCompleteListener { task ->
//                progress_bar_save_change.visibility = View.INVISIBLE
//                if (task.isSuccessful) {
//                    context?.toast("Profile Update")
//                } else {
//                    context?.toast(task.exception?.message!!)
//                }
//            }
//        }
//        tv_verified.setOnClickListener {
//            currentUser?.sendEmailVerification()
//                ?.addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        context?.toast("Verivication email sent")
//                    } else {
//                        context?.toast(it.exception?.message!!)
//                    }
//                }
//        }
//        tv_pass_profile.setOnClickListener {
//            view.findNavController().navigate(R.id.action_profileFragment_to_updatePasswordFragment)
//        }
//
//        image_view_profile.setOnClickListener {
//            takePictureIntent()
//        }
    }

//    private fun takePictureIntent() {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { pictureIntent ->
//            pictureIntent.resolveActivity(activity?.packageManager!!)?.also {
//                startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE)
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            val imageBitMap = data?.extras?.get("data") as Bitmap
//
//            uploadImageAndSaveUri(imageBitMap)
//        }
//    }
//
//    private fun uploadImageAndSaveUri(bitmap: Bitmap) {
//        val baos = ByteArrayOutputStream()
//        val storageRef = FirebaseStorage.getInstance()
//            .reference
//            .child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")
//
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//
//        val image = baos.toByteArray()
//
//        val upload = storageRef.putBytes(image)
//
//        progresbar_image.visibility = View.VISIBLE
//        upload.addOnCompleteListener { uploadTask ->
//            progresbar_image.visibility = View.INVISIBLE
//            if (uploadTask.isSuccessful) {
//                storageRef.downloadUrl.addOnCompleteListener { urlTask ->
//                    urlTask.result?.let {
//                        imageUri = it
//                        activity?.toast(imageUri.toString())
//
//                        image_view_profile.setImageBitmap(bitmap)
//                    }
//                }
//            } else {
//                uploadTask.exception?.let {
//                    activity?.toast(it.message!!)
//                }
//            }
//        }
//    }
}
