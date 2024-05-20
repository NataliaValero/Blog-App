package com.example.blogapp.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blogapp.R


@Composable
fun RegularTextField(modifier: Modifier = Modifier, label : String) {

     var textValue by remember {
         mutableStateOf("")
     }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        label = { Text(text = label) },
        value = textValue,
        onValueChange = {
            textValue = it
        },
        keyboardOptions = KeyboardOptions.Default,
        colors = TextFieldDefaults.colors(
            focusedLabelColor = colorResource(id = R.color.text_input_layout_stroke_color),
            focusedPlaceholderColor = colorResource(id = R.color.white),
            cursorColor = colorResource(id = R.color.text_input_layout_stroke_color),
            focusedContainerColor = colorResource(id = R.color.white),
            unfocusedContainerColor = colorResource(id = R.color.white)
        )
    )
}

@Composable
fun PasswordTextField(modifier: Modifier = Modifier, label : String) {

    var password by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }


    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        label = { Text(text = label) },
        value = password,
        onValueChange = {
            password = it
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
             val iconImage = if(passwordVisible) {
                 Icons.Filled.Visibility
             } else {
                 Icons.Filled.VisibilityOff
             }

            val description = if(passwordVisible) {
                stringResource(id = R.string.hide_password)
            } else {
                stringResource(id = R.string.show_password)
            }

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }

        },
        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            focusedLabelColor = colorResource(id = R.color.text_input_layout_stroke_color),
            focusedPlaceholderColor = colorResource(id = R.color.white),
            cursorColor = colorResource(id = R.color.text_input_layout_stroke_color),
            focusedContainerColor = colorResource(id = R.color.white),
            unfocusedContainerColor = colorResource(id = R.color.white)
        )
    )
}


@Composable
fun ButtonComponent(modifier: Modifier = Modifier, textButton : String) {

    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(45.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.blue),
            contentColor = Color.White
        )
    ) {

        Text(text = textButton,
            fontSize = 16.sp)
    }
}