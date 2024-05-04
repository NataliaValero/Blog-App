package com.example.blogapp.ui.auth

import android.os.Bundle
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
import com.example.blogapp.databinding.FragmentRegisterBinding
import com.example.blogapp.domain.auth.AuthRepositoryImpl
import com.example.blogapp.presentation.auth.AuthViewModel
import com.example.blogapp.presentation.auth.AuthViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepositoryImpl(AuthDataSource(FirebaseAuth.getInstance())))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        signUp()

    }

    private fun signUp() {

        binding.btnSignup.setOnClickListener {

            val username = binding.editTextUsername.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPass.text.toString().trim()

            if (validateCredentials(username, email, password, confirmPassword)) {
                createUser(username, email, password)
            }
        }
    }

    private fun createUser(username: String, email: String, password: String) {
        viewModel.signUp(username, email, password).observe(viewLifecycleOwner) {

            when (it) {
                is Result.Loading -> {
                    binding.progressBar.show()
                    binding.btnSignup.isEnabled = false
                }

                is Result.Success -> {
                    binding.progressBar.hide()
                    findNavController().navigate(R.id.action_registerFragment_to_setupProfileFragment)
                }

                is Result.Failure -> {
                    binding.progressBar.hide()
                    binding.btnSignup.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        "Error: ${it.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun validateCredentials(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {


        if (username.isEmpty()) {
            binding.editTextUsername.error = "Username is empty"
            return false
        }

        if (email.isEmpty()) {
            binding.editTextEmail.error = "E-mail is empty"
            return false
        }

        val pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
        val matcher = pattern.matcher(email)

        if (!matcher.matches()) {
            binding.editTextEmail.error = "E-mail is not valid"
            return false
        }

        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is empty"
            return false
        }

        if (password.length < 6) {
            binding.editTextPassword.error = "Password must be at least 6 characters"
            return false
        }

        if (confirmPassword.isEmpty()) {
            binding.editTextConfirmPass.error = "Confirm password is empty"
            return false
        }

        if (password != confirmPassword) {
            binding.editTextConfirmPass.error = "Password does not match"
            return false
        } else {
            return true
        }
    }

}