package com.example.blogapp.ui.auth.login

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blogapp.R
import com.example.blogapp.presentation.auth.AuthViewModel
import com.example.blogapp.ui.components.BoldTextComponent
import com.example.blogapp.ui.components.ButtonComponent
import com.example.blogapp.ui.components.ButtonWithProgressBar
import com.example.blogapp.ui.components.PasswordTextField
import com.example.blogapp.ui.components.RegularTextComponent
import com.example.blogapp.ui.components.RegularTextField

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
) {


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
                    .height(70.dp))

            Spacer(modifier = Modifier.height(20.dp))

            RegularTextField(
                label = stringResource(id = R.string.email),
                onValueChangeListener = {},
                errorMessage = null)

            PasswordTextField(
                label = stringResource(id = R.string.password),
                onValueChangeListener =  {},
                errorMessage = null)

            ButtonWithProgressBar(isLoading = false) {
                
            }

            Spacer(modifier = Modifier.height(60.dp))
            Row {
                RegularTextComponent(
                    text = stringResource(id = R.string.dont_have_account),
                    color = R.color.black)

                Spacer(modifier = Modifier.width(5.dp))

                BoldTextComponent(
                    modifier = Modifier.clickable {

                    },
                    text = stringResource(id = R.string.sign_up),
                    color = R.color.blue)
            }

        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}