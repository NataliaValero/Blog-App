package com.example.blogapp.ui.auth.setupProfile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.blogapp.R
import com.example.blogapp.core.Result
import com.example.blogapp.data.remote.auth.AuthDataSource
import com.example.blogapp.databinding.FragmentSetupProfileBinding
import com.example.blogapp.domain.auth.AuthRepositoryImpl
import com.example.blogapp.presentation.auth.AuthViewModelFactory
import com.example.blogapp.presentation.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {

    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepositoryImpl(AuthDataSource(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance(), FirebaseStorage.getInstance())))
    }

    private lateinit var binding: FragmentSetupProfileBinding
    private lateinit var imageView: ImageView
    private var imageUri: Uri? = null
    private lateinit var contract: ActivityResultLauncher<Uri>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetupProfileBinding.bind(view)

        imageView = binding.profileImage

        takePictureContract()


        binding.profileImage.setOnClickListener {
            imageUri = createImageUri()
            imageUri?.let {
                contract.launch(it)
            }
        }

        binding.btnCreateProfile.setOnClickListener {
            // Obtain photo an username
            val username = binding.usernameTxt.text.toString().trim()

            imageUri?.let {
                if (username.isNotEmpty()) {
                    viewModel.updateUserProfile(imageUri!!, username)
                        .observe(viewLifecycleOwner) { result ->

                            when (result) {
                                is Result.Loading -> {
                                    Toast.makeText(requireContext(), "Uploading photo...", Toast.LENGTH_SHORT).show()
                                    binding.btnCreateProfile.isEnabled = false
                                }

                                is Result.Success<*>-> {

                                    findNavController().navigate(R.id.action_setupProfileFragment_to_homeScreenFragment)
                                }

                                is Result.Failure -> {
                                }

                            }
                        }
                }
            }
        }
    }

    private fun createImageUri(): Uri {
        val imageFileName = "camera_photos.png"
        val storageDir = File(requireContext().filesDir, "images")

        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        val image = File(storageDir, imageFileName)
        return FileProvider.getUriForFile(requireContext(), "com.example.blogapp.provider", image)
    }


    private fun takePictureContract() {

        imageUri = createImageUri()

        // Inicializar el contrato para capturar imágenes
        contract = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            imageView.setImageURI(null)

            if (success) {
                // Si la captura de imagen fue exitosa, mostrar la imagen en el ImageView
                Log.d("CameraFragment", "Se tomó la foto exitosamente")
                imageView.setImageURI(imageUri)
                binding.btnCreateProfile.isEnabled = true

            } else {
                // Si la captura de imagen fue cancelada o fallida, mostrar un mensaje de error
                Log.e("CameraFragment", "Error al capturar la imagen.")
                imageView.setImageURI(null)
            }

        }

    }
}