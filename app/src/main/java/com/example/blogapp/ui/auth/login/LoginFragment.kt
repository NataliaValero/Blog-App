package com.example.blogapp.ui.auth.login

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
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


class LoginFragment() : Fragment() {



    private lateinit var loginComposeView : ComposeView

    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepositoryImpl(
                AuthDataSource(
                    FirebaseAuth.getInstance(),
                    FirebaseFirestore.getInstance(),
                    FirebaseStorage.getInstance()
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).also {
            loginComposeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loginComposeView.setContent {
            val navController = findNavController()

            MaterialTheme {
                LoginScreen(authViewModel = viewModel, navController = navController)
            }
        }

        isUserLoggedIn()
    }

    private fun isUserLoggedIn() {
        viewModel.currentUser?.let {

            // Asegura que el usuario logeado suba foto de perfil y username en caso de no tenerlo
            if (!it.displayName.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
            } else {
                findNavController().navigate(R.id.action_loginFragment_to_setupProfileFragment)
            }
        }

    }
}