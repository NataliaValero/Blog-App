package com.example.blogapp.ui.auth.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.material3.MaterialTheme
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import com.example.blogapp.R
import com.example.blogapp.core.Result
import com.example.blogapp.core.hide
import com.example.blogapp.core.show
import com.example.blogapp.data.remote.auth.AuthDataSource
import com.example.blogapp.databinding.FragmentRegisterBinding
import com.example.blogapp.domain.auth.AuthRepositoryImpl
import com.example.blogapp.presentation.auth.AuthViewModel
import com.example.blogapp.presentation.auth.AuthViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepositoryImpl(AuthDataSource(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance(), FirebaseStorage.getInstance())))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        //signUp()

        compose()

    }

//    private fun signUp() {
//
//        binding.btnSignup.setOnClickListener {
//
//            val username = binding.editTextUsername.text.toString().trim()
//            val email = binding.editTextEmail.text.toString().trim()
//            val password = binding.editTextPassword.text.toString().trim()
//            val confirmPassword = binding.editTextConfirmPass.text.toString().trim()
//
//            suscribeCredentialObservers()
//            setTextlistener()
//
//            if (viewModel.validateRegistration(username, email, password, confirmPassword)) {
//                createUser(username, email, password)
//            }
//        }
//    }
//
//    private fun createUser(username: String, email: String, password: String) {
//        viewModel.signUp(username, email, password).observe(viewLifecycleOwner) {
//
//            when (it) {
//                is Result.Loading -> {
//                    binding.progressBar.show()
//                    binding.btnSignup.isEnabled = false
//                }
//
//                is Result.Success -> {
//                    binding.progressBar.hide()
//                    findNavController().navigate(R.id.action_registerFragment_to_setupProfileFragment)
//                }
//
//                is Result.Failure -> {
//                    binding.progressBar.hide()
//                    binding.btnSignup.isEnabled = true
//                    Toast.makeText(
//                        requireContext(),
//                        "Error: ${it.exception}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
//    }
//
//
//    fun suscribeCredentialObservers() = with(binding) {
//        viewModel.usernameError.observe(viewLifecycleOwner) {
//
//            usernameTxtField.isErrorEnabled = false
//            it?.let {
//                usernameTxtField.error = it
//            }
//        }
//        viewModel.emailError.observe(viewLifecycleOwner) {
//            emailTxtField.isErrorEnabled = false
//            it?.let {
//                emailTxtField.error = it
//            }
//        }
//
//        viewModel.passwordError.observe(viewLifecycleOwner) {
//            passwordTextinputLayout.isErrorEnabled = false
//            it?.let {
//                passwordTextinputLayout.error = it
//            }
//        }
//        viewModel.confirmPasswordError.observe(viewLifecycleOwner) {
//            confirmTextinputLayout.isErrorEnabled = false
//            it?.let {
//                confirmTextinputLayout.error = it
//            }
//        }
//
//    }
//
//    private fun setTextlistener() = with(binding) {
//        editTextUsername.addTextChangedListener {
//            viewModel.setUsernameError(null)
//        }
//
//        editTextEmail.addTextChangedListener {
//            viewModel.setEmailError(null)
//        }
//
//        editTextPassword.addTextChangedListener{
//            viewModel.setPasswordError(null)
//        }
//
//        editTextConfirmPass.addTextChangedListener {
//            viewModel.setConfirmPasswordError(null)
//        }
//
//    }

    fun compose() {
        binding.composeView.setContent {

            val navController = findNavController()
            // Compose land
            MaterialTheme {
                RegisterScreen(authViewModel = viewModel, navController = navController)
            }

        }
    }

}