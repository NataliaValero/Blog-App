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


    fun validateRegistration(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        val usernameResult = CredentialsValidator.validateUsername(username)
        val emailResult = CredentialsValidator.validateEmail(email)
        val passwordResult = CredentialsValidator.validatePassword(password)
        val confirmPasswordResult =
            CredentialsValidator.validateConfirmPassword(confirmPassword, password)

        val hasError = listOf(
            usernameResult,
            emailResult,
            passwordResult,
            confirmPasswordResult
        ).any {
            !it.isValid
        }


        usernameError.value = usernameResult.errorMessage
        emailError.value = emailResult.errorMessage
        passwordError.value = passwordResult.errorMessage
        confirmPasswordError.value = confirmPasswordResult.errorMessage


        return !hasError
    }

    fun validateLogin(email: String, password: String): Boolean {
        val emailResult = CredentialsValidator.validateEmail(email)
        val passwordResult = CredentialsValidator.validatePassword(password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any {
            !it.isValid
        }

        emailError.value = emailResult.errorMessage
        passwordError.value = passwordResult.errorMessage


        return !hasError
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