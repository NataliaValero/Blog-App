package com.example.blogapp.ui.components

import android.util.Log
import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blogapp.R

@Composable
fun RegularTextComponent(
    modifier: Modifier = Modifier,
    text: String,
   @ColorRes color: Int) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 16.sp,
        fontStyle = FontStyle.Normal,
        color = colorResource(id = color)
    )
}

@Composable
fun BoldTextComponent(
    modifier: Modifier = Modifier,
    text: String,
    @ColorRes color: Int) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = colorResource(id = color)
    )
}

@Composable
fun RegularTextField(
    modifier: Modifier = Modifier,
    label: String,
    onValueChangeListener: (String) -> Unit,
    errorMessage :String?
    ) {

    val isError = errorMessage != null

    var textValue by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        label = { Text(text = label) },
        value = textValue,
        onValueChange = {
            textValue = it.trim()
            onValueChangeListener(textValue)

        },
        isError = isError,
        keyboardOptions = KeyboardOptions.Default,
        colors = TextFieldDefaults.colors(
            focusedLabelColor = colorResource(id = R.color.text_input_layout_stroke_color),
            cursorColor = colorResource(id = R.color.text_input_layout_stroke_color),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        supportingText = {
            if(errorMessage != null) {
                Text(text = errorMessage, style = MaterialTheme.typography.bodyMedium)
            }
        }
    )


}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    label: String,
    onValueChangeListener: (String) -> Unit,
    errorMessage: String?
) {
    val isError = errorMessage != null

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
            password = it.trim()
            onValueChangeListener(password)
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val iconImage = if (passwordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            val description = if (passwordVisible) {
                stringResource(id = R.string.hide_password)
            } else {
                stringResource(id = R.string.show_password)
            }

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }

        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            focusedLabelColor = colorResource(id = R.color.text_input_layout_stroke_color),
            cursorColor = colorResource(id = R.color.text_input_layout_stroke_color),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        supportingText = {
            if(errorMessage != null) {
                Text(text = errorMessage,
                    style = MaterialTheme.typography.bodyMedium)
            }
        }
    )
}


@Composable
fun ButtonComponent(modifier: Modifier = Modifier, textButton: String, enabled: Boolean, onButtonClick: () -> Unit) {

    Button(
        onClick = {
            onButtonClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(45.dp),
        contentPadding = PaddingValues(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.blue),
            contentColor = Color.White
        )
    ) {

        Text(
            text = textButton,
            fontSize = 16.sp
        )
    }
}

@Composable
fun ProgressBarComponent(modifier: Modifier = Modifier) {

    CircularProgressIndicator(
        modifier = modifier.height(45.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant
    )
}


@Composable
fun ButtonWithProgressBar(modifier : Modifier = Modifier, isLoading: Boolean, onButtonClick: () -> Unit) {

    // Columna de boton con progress bar
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {

        ButtonComponent(
            modifier = modifier,
            textButton = stringResource(id = R.string.sign_up),
            enabled = !isLoading,
            onButtonClick = {
                onButtonClick()
            }
        )

        if (isLoading) {
            ProgressBarComponent()
        }
    }

}

@Preview
@Composable
fun ComposablePreview() {

    Column {
        ButtonWithProgressBar(isLoading = false, onButtonClick = {})
    }


}