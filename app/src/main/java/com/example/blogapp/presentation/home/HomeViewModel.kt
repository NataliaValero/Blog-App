package com.example.blogapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.blogapp.core.Result
import com.example.blogapp.domain.home.HomeScreenRepository
import kotlinx.coroutines.Dispatchers

class HomeScreenViewModel(private val repository: HomeScreenRepository) : ViewModel() {


    fun fetchLatestPosts() = liveData(Dispatchers.IO) {


        emit(Result.Loading())

        kotlin.runCatching {
            repository.getLatestPost()
        }.onSuccess {
            emit(it)
        }.onFailure {
            emit(Result.Failure(Exception(it.message)))
        }
    }


    fun registerLikeButtonState(postId: String, liked: Boolean) =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {


            emit(Result.Loading())

            kotlin.runCatching {
                repository.registerLikeButtonState(postId, liked)
            }.onSuccess {
                emit(Result.Success(Unit))
            }.onFailure {
                emit(Result.Failure(Exception(it.message)))
            }
        }


}

class HomeScreenViewModelFactory(private val repository: HomeScreenRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeScreenRepository::class.java).newInstance(repository)
    }
}