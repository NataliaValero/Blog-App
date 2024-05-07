package com.example.blogapp.core

import android.util.Patterns

object CredentialsValidator {


    fun validateUsername(username: String): ValidationResult {

        if (username.isEmpty()) {
            return ValidationResult(false, "Username is empty")
        } else {
            return ValidationResult()
        }
    }

    fun validateEmail(email: String) : ValidationResult {

        if(email.isEmpty()) {
            return ValidationResult(false, "E-mail is empty")
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(false, "E-mail is not valid")
        } else {
            return ValidationResult()
        }
    }

    fun validatePassword(password: String) : ValidationResult {

        if(password.isEmpty()) {
            return ValidationResult(false, "Password is empty")
        } else if (password.length < 6) {
            return ValidationResult(false, "Password must be at least 6 characters")
        } else {
            return ValidationResult()
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String) : ValidationResult {

        if(confirmPassword.isEmpty()) {
            return ValidationResult(false, "Confirm password is empty")
        }else if (confirmPassword != password) {
            return ValidationResult(false, "Password does not match")
        } else {
            return ValidationResult()
        }
    }

}

data class ValidationResult(val isValid: Boolean = true, val errorMessage: String? = null)