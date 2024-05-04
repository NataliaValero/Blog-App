package com.example.blogapp.presentation.camera

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.blogapp.core.Result
import com.example.blogapp.domain.camera.CameraRepository
import kotlinx.coroutines.Dispatchers

class CameraViewModel(private val repository: CameraRepository): ViewModel() {

    fun uploadPhoto(imageUrl: Uri, description: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(repository.uploadPhoto(imageUrl, description)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}


class CameraViewModelFactory(private val repository: CameraRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CameraViewModel(repository) as T
    }
}