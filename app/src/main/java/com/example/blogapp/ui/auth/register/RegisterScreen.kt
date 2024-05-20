package com.example.blogapp.ui.auth.register


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blogapp.R
import com.example.blogapp.ui.components.ButtonComponent
import com.example.blogapp.ui.components.PasswordTextField
import com.example.blogapp.ui.components.RegularTextField

@Composable
fun RegisterScreen(modifier: Modifier = Modifier) {


    Surface (
        modifier = modifier,
        color = Color.White

    ){
        Column(
            modifier = modifier.padding(28.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RegularTextField(label = stringResource(id = R.string.username))
            RegularTextField(label = stringResource(id = R.string.email))
            PasswordTextField(label = stringResource(id = R.string.password))
            PasswordTextField(label = stringResource(id = R.string.confirm_password))
            Spacer(modifier = Modifier.heightIn(30.dp))
            ButtonComponent(textButton = stringResource(id = R.string.sign_up))
        }
    }

}

@Preview
@Composable
fun RegisterScreenPreview() {

    RegisterScreen()


}