package com.example.blogapp.ui.auth.register


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blogapp.R
import com.example.blogapp.core.Result
import com.example.blogapp.presentation.auth.AuthViewModel
import com.example.blogapp.ui.components.ButtonWithProgressBar
import com.example.blogapp.ui.components.PasswordTextField
import com.example.blogapp.ui.components.RegularTextField

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    navController: NavController
) {


    // Estados locales para almacenar los valores de los campos de texto
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


    val usernameError = authViewModel.usernameErrorState.value
    val emailError = authViewModel.emailErrorState.value
    val passwordError = authViewModel.passwordErrorState.value
    val confirmPasswordError = authViewModel.confirmPasswordErrorState.value


    var isLoading by remember {
        mutableStateOf(false)
    }


    val signUpState by authViewModel.signUpState.observeAsState()

    //val signUpState by authViewModel.signUp(username, email, password).observeAsState()

    LaunchedEffect(signUpState) {
        when (signUpState) {
            is Result.Loading -> {
                // For progress bar status
                isLoading = true
            }

            is Result.Success -> {

                isLoading = false
                navController.navigate(R.id.action_registerFragment_to_setupProfileFragment)
            }

            is Result.Failure -> {
                isLoading = false
            }

            else -> {

            }
        }
    }


    Surface(
        modifier = modifier.padding(28.dp),
        color = Color.Transparent
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Image(
                painter = painterResource(id = R.drawable.instagram_logo), 
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
                )

            Spacer(modifier = Modifier.height(30.dp))

            RegularTextField(
                label = stringResource(id = R.string.username),
                onValueChangeListener = {
                    username = it
                    authViewModel.setUsernameStateError(null)
                },
                errorMessage = usernameError
            )

            RegularTextField(
                label = stringResource(id = R.string.email),
                onValueChangeListener = {
                    email = it
                    authViewModel.setEmailStateError(null)

                },
                errorMessage = emailError
            )

            PasswordTextField(
                label = stringResource(id = R.string.password),
                onValueChangeListener = {
                    password = it
                    authViewModel.setPasswordStateError(null)
                },
                errorMessage = passwordError
            )

            PasswordTextField(
                label = stringResource(id = R.string.confirm_password),
                onValueChangeListener = {
                    confirmPassword = it
                    authViewModel.setConfirmPasswordStateError(null)
                },
                errorMessage = confirmPasswordError
            )


            Spacer(modifier = Modifier.heightIn(30.dp))


            ButtonWithProgressBar(isLoading = isLoading, onButtonClick = {

                if (authViewModel.validateRegistration(
                        username = username,
                        email = email,
                        password = password,
                        confirmPassword = confirmPassword)
                    ) {
                    // create user
                    Log.d(
                        "Compose register user",
                        "user with email $email was validated"
                    )

                    authViewModel.signUp(username, email, password)


                } else {
                    Log.d("Compose register user", "error")
                }

            })
        }
    }
}

