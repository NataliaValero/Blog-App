package com.example.blogapp.core

import android.util.Patterns

class CredentialsValidator {

    companion object {
        fun validate(
            username: String,
            email: String,
            password: String,
            confirmPassword: String
        ): Map<String, String> {

            val errors = mutableMapOf(
                "username" to "",
                "email" to "",
                "password" to "",
                "confirmPassword" to ""
            )

            if (username.isEmpty()) {
                errors["username"] = "Username is empty"
            }

            if (email.isEmpty()) {
                errors["email"] = "E-mail is empty"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                errors["email"] = "E-mail is not valid"
            }

            if (password.isEmpty()) {
                errors["password"] = "Password is empty"
            } else if (password.length < 6) {
                errors["password"] = "Password must be at least 6 characters"
            }

            if (confirmPassword.isEmpty()) {
                errors["confirmPassword"] = "Confirm password is empty"
            } else if (confirmPassword != password) {
                errors["confirmPassword"] = "Password does not match"
            }

            return errors
        }
    }

}