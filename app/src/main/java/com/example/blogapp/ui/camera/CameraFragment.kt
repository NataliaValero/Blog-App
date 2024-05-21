package com.example.blogapp.ui.camera


import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.blogapp.R
import com.example.blogapp.core.Result
import com.example.blogapp.data.remote.camera.CameraDataSource
import com.example.blogapp.databinding.FragmentCameraBinding
import com.example.blogapp.domain.camera.CameraRepositoryImpl
import com.example.blogapp.presentation.camera.CameraViewModel
import com.example.blogapp.presentation.camera.CameraViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID


class CameraFragment : Fragment(R.layout.fragment_camera) {

    private lateinit var binding: FragmentCameraBinding
    private lateinit var imageView: ImageView
    private  var imageUri: Uri? = null
    private lateinit var contract : ActivityResultLauncher<Uri>

    private val viewModelCamera by viewModels<CameraViewModel> {
        CameraViewModelFactory(CameraRepositoryImpl(CameraDataSource(FirebaseStorage.getInstance())))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCameraBinding.bind(view)


        // Inicializar vista
        imageView = binding.photoImage

        // Launch camera when fragment is created
        takePictureContract()



        val btnTakePicture = binding.btnUploadPhoto
        btnTakePicture.setOnClickListener {

            // Obtener image uri y description

            val postDescription = binding.descriptionTxt.text.toString().trim()
            val imageUrl = imageUri



            imageUrl?.let {
                viewModelCamera.uploadPhoto(imageUrl, postDescription).observe(viewLifecycleOwner) {result->

                    when(result) {
                        is Result.Loading -> {
                            Toast.makeText(requireContext(), "Uploading photo...", Toast.LENGTH_SHORT).show()
                            btnTakePicture.isEnabled = false
                        }
                        is Result.Success<*> -> {
                            findNavController().navigate(R.id.action_cameraFragment_to_homeScreenFragment)
                            binding.descriptionTxt.text.clear()
                        }
                        is Result.Failure -> {

                        }

                    }

                }
            }
        }

        binding.photoImage.setOnClickListener {
            imageUri = createImageUri()
            imageUri?.let {
                contract.launch(it)
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
        contract = registerForActivityResult(ActivityResultContracts.TakePicture()) { success->
            imageView.setImageURI(null)

            if(success) {
                // Si la captura de imagen fue exitosa, mostrar la imagen en el ImageView
                Log.d("CameraFragment", "Se tomó la foto exitosamente")
                imageView.setImageURI(imageUri)
                binding.btnUploadPhoto.isEnabled = true


                // Subir la imagen a Firebase Storage
                //uploadPicture(imageUri)
            } else {
                // Si la captura de imagen fue cancelada o fallida, mostrar un mensaje de error
                Log.e("CameraFragment", "Error al capturar la imagen.")
                imageView.setImageURI(null)
                imageUri = null
            }

        }

        // launch camera
        contract.launch(imageUri)
    }


}