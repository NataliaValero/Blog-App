package com.example.blogapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.blogapp.core.Result
import com.example.blogapp.data.model.Post
import com.example.blogapp.domain.home.HomeScreenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: HomeScreenRepository) : ViewModel() {


    private val _fetchData : MutableLiveData<Result<List<Post>>> = MutableLiveData()
    val fetchData : LiveData<Result<List<Post>>> = _fetchData

    init {
        fetchDataPost()
    }

    fun fetchDataPost() {
        viewModelScope.launch {

            _fetchData.value = Result.Loading()

            try {
                val result = repository.getLatestPost()
                _fetchData.value = result
            } catch (e: Exception) {
                _fetchData.value = Result.Failure(e)
            }
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