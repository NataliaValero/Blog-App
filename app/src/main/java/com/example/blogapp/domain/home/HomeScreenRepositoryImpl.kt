package com.example.blogapp.domain.home

import com.example.blogapp.core.Result
import com.example.blogapp.data.model.Post
import com.example.blogapp.data.remote.home.HomeScreenDataSource
import kotlinx.coroutines.flow.Flow

class HomeScreenRepositoryImpl(private val dataSource: HomeScreenDataSource): HomeScreenRepository {

    override suspend fun getLatestPost(): Result<List<Post>> {
        return dataSource.getLatestPost()
    }



    override suspend fun registerLikeButtonState(postId: String, liked: Boolean)  {
        dataSource.registerLikeButtonState(postId, liked)
    }




}