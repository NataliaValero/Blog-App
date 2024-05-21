package com.example.blogapp.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.blogapp.R
import com.example.blogapp.core.Result
import com.example.blogapp.core.hide
import com.example.blogapp.core.show
import com.example.blogapp.data.remote.auth.AuthDataSource
import com.example.blogapp.databinding.FragmentLoginBinding
import com.example.blogapp.domain.auth.AuthRepositoryImpl
import com.example.blogapp.presentation.auth.AuthViewModel
import com.example.blogapp.presentation.auth.AuthViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepositoryImpl(AuthDataSource(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance(), FirebaseStorage.getInstance())))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        isUserLoggedIn()
        doLogin()
        goToSignUpPage()

    }

    private fun isUserLoggedIn() {
        viewModel.currentUser?.let {

            // Asegura que el usuario logeado suba foto de perfil y username en caso de no tenerlo

            if (it.displayName.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_loginFragment_to_setupProfileFragment)
            } else {
                findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
            }
        }

    }

    private fun doLogin() {
        binding.btnSignin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            suscribeCredentialsObservers()
            setTextlistener()

            if (viewModel.validateLogin(email, password)) {
                signIn(email, password)
            }
        }
    }

    private fun signIn(email: String, password: String) {

        viewModel.signIn(email, password).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    binding.progressBar.show()
                    binding.btnSignin.isEnabled = false
                }

                is Result.Success -> {
                    binding.progressBar.hide()

                    if (it.data?.displayName.isNullOrEmpty()) {
                        findNavController().navigate(R.id.action_loginFragment_to_setupProfileFragment)

                    } else {
                        findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                    }

                    Log.d("user", "${it.data?.displayName.toString()}")

                }

                is Result.Failure -> {
                    binding.progressBar.hide()
                    binding.btnSignin.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        "Error: ${it.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }

    private fun suscribeCredentialsObservers() = with(binding) {
        viewModel.emailError.observe(viewLifecycleOwner) {

            emailTxtField.isErrorEnabled = false
            it?.let {
                emailTxtField.error = it
            }
        }

        viewModel.passwordError.observe(viewLifecycleOwner) {

            passwordTxtField.isErrorEnabled = false
            it?.let {
                passwordTxtField.error = it
            }
        }
    }

    private fun setTextlistener() = with(binding) {
        editTextEmail.addTextChangedListener {
            viewModel.setEmailError(null)
        }

        editTextPassword.addTextChangedListener{
            viewModel.setPasswordError(null)
        }

    }


    private fun goToSignUpPage() {
        binding.txtSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }


}