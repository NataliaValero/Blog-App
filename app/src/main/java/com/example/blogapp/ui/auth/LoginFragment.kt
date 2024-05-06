package com.example.blogapp.ui.auth

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
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
import java.util.regex.Pattern


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepositoryImpl(AuthDataSource(FirebaseAuth.getInstance())))
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

            validateCredentials(email, password)
            signIn(email, password)
        }
    }


    private fun validateCredentials(email: String, password: String) {

        // Verify that email is not empty
        if (email.isEmpty()) {
            binding.editTextEmail.error = "E-mail is empty"
            return
        }

        // Verify that email is valid


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.error = "E-mail is not valid"
            return
        }

        // Verify that password is not empty

        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is empty"
            return
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


    private fun goToSignUpPage() {
        binding.txtSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }


}