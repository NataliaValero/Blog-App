package com.example.blogapp.presentation.auth

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.blogapp.core.CredentialsValidator
import com.example.blogapp.core.Result
import com.example.blogapp.data.model.User
import com.example.blogapp.domain.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AuthViewModel(private val repository: AuthRepository) : ViewModel() {



    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()


    val usernameErrorState : MutableState<String?> = mutableStateOf(null)
    val emailErrorState : MutableState<String?> = mutableStateOf(null)
    val passwordErrorState : MutableState<String?> = mutableStateOf(null)
    val confirmPasswordErrorState : MutableState<String?> = mutableStateOf(null)



    //private val _signUpState = mutableStateOf(Result)
    val signUpState:MutableLiveData<Result<FirebaseUser?>> = MutableLiveData()





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


        usernameErrorState.value = usernameResult.errorMessage
        emailErrorState.value = emailResult.errorMessage
        passwordErrorState.value = passwordResult.errorMessage
        confirmPasswordErrorState.value = confirmPasswordResult.errorMessage



        emailError.value = emailResult.errorMessage
        passwordError.value = passwordResult.errorMessage



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


    fun setEmailError(newError: String?) {
        emailError.value = newError
    }
    fun setPasswordError(newError: String?) {
        passwordError.value = newError
    }


    fun setUsernameStateError(newError: String?) {
        usernameErrorState.value = newError
    }

    fun setEmailStateError(newError: String?) {
        emailErrorState.value = newError
    }

    fun setPasswordStateError(newError: String?) {
        passwordErrorState.value = newError
    }

    fun setConfirmPasswordStateError(newError: String?) {
        confirmPasswordErrorState.value = newError
    }

//    fun signUp(username: String, email: String, password: String) = liveData(Dispatchers.IO) {
//
//        emit(Result.Loading())
//
//        try {
//            emit(Result.Success(repository.signUp(username, email, password)))
//        } catch (e: Exception) {
//            emit(Result.Failure(e))
//        }
//    }

    fun signUp(username: String, email: String, password: String) {
        viewModelScope.launch {

            signUpState.value = Result.Loading()

            try {
                val result = repository.signUp(username, email, password)
                signUpState.value = Result.Success(result)
            } catch (e: Exception) {
                signUpState.value = Result.Failure(e)
            }

        }
    }

//    fun signIn(email: String, password: String) {
//        viewModelScope.launch {
//
//            _signUpState.value = Result.Loading
//
//            try {
//                val result = repository.signIn(email, password)
//                _signUpState.value = Result.Success(result)
//            } catch (e: Exception) {
//                _signUpState.value = Result.Failure(e)
//            }
//
//        }
//    }



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