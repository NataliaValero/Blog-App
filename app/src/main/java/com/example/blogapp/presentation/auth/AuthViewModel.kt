package com.example.blogapp.presentation.auth

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.blogapp.core.CredentialsValidator
import com.example.blogapp.core.Result
import com.example.blogapp.domain.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers


class AuthViewModel(private val repository: AuthRepository) : ViewModel() {


    val usernameError = MutableLiveData<String?>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()
    val confirmPasswordError = MutableLiveData<String?>()

    fun validateCredentials(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        val errors = CredentialsValidator.validate(username, email, password, confirmPassword)

        var result = true

        val allErrorsEmpty = errors.values.all { it.isEmpty() }

        if (!allErrorsEmpty) {
            errors.forEach { (field, error) ->

                when (field) {
                    "username" -> usernameError.value = error
                    "email" -> emailError.value = error
                    "password" -> passwordError.value = error
                    "confirmPassword" -> confirmPasswordError.value = error
                }
            }

            result = false
        }

        return result
    }

    fun signUp(username: String, email: String, password: String) = liveData(Dispatchers.IO) {

        emit(Result.Loading())

        try {
            emit(Result.Success(repository.signUp(username, email, password)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun signIn(email: String, password: String) = liveData(Dispatchers.IO) {

        emit(Result.Loading())

        try {
            emit(Result.Success(repository.signIn(email, password)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun updateUserProfile(profilePic: Uri, username: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(repository.updateProfile(profilePic, username)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }


    val currentUser: FirebaseUser?
        get() = repository.currentUser

    fun logOut() {
        repository.logOut()
    }


}

class AuthViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }
}