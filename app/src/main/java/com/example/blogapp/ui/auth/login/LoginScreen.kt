package com.example.blogapp.ui.auth.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blogapp.R
import com.example.blogapp.core.Result
import com.example.blogapp.presentation.auth.AuthViewModel
import com.example.blogapp.ui.components.BoldTextComponent
import com.example.blogapp.ui.components.ButtonComponent
import com.example.blogapp.ui.components.ButtonWithProgressBar
import com.example.blogapp.ui.components.PasswordTextField
import com.example.blogapp.ui.components.RegularTextComponent
import com.example.blogapp.ui.components.RegularTextField
import com.google.firebase.auth.FirebaseUser

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    navController: NavController
) {


    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }


    val emailErrorMessage = authViewModel.emailErrorState.value
    val passwordErrorMessage = authViewModel.passwordErrorState.value


    val signInState by authViewModel.signInState.observeAsState()

    LaunchedEffect(signInState) {

        when(signInState) {
            is Result.Loading -> {
                isLoading = true
            }
            is Result.Success -> {
                isLoading = false
                if(!authViewModel.currentUser?.displayName.isNullOrEmpty()) {
                    navController.navigate(R.id.action_loginFragment_to_homeScreenFragment)
                } else {
                    navController.navigate(R.id.action_loginFragment_to_setupProfileFragment)
                }
            }
            is Result.Failure -> {
                isLoading = false
            }

            else -> {}
        }
    }


    Surface(
        modifier = modifier.padding(28.dp),
        color = Color.Transparent
    ) {

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.instagram_logo),
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .height(70.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            RegularTextField(
                label = stringResource(id = R.string.email),
                onValueChangeListener = {
                    email = it
                    authViewModel.setEmailStateError(null)},
                errorMessage = emailErrorMessage
            )

            PasswordTextField(
                label = stringResource(id = R.string.password),
                onValueChangeListener = {
                    password = it
                    authViewModel.setPasswordStateError(null)
                },
                errorMessage = passwordErrorMessage
            )

            ButtonWithProgressBar(isLoading = isLoading, onButtonClick = {


                if(authViewModel.validateLogin(email, password)) {
                    authViewModel.signIn(email, password)
                    Log.d("Current user", "${authViewModel.currentUser?.displayName}")
                }
            })

            Spacer(modifier = Modifier.height(60.dp))
            Row {
                RegularTextComponent(
                    text = stringResource(id = R.string.dont_have_account),
                    color = R.color.black
                )

                Spacer(modifier = Modifier.width(5.dp))

                BoldTextComponent(
                    modifier = Modifier.clickable {
                        navController.navigate(R.id.action_loginFragment_to_registerFragment)
                    },
                    text = stringResource(id = R.string.sign_up),
                    color = R.color.blue
                )
            }
        }
    }
}

