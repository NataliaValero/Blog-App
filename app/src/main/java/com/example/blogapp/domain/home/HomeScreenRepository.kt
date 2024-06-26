package com.example.blogapp.domain.home

import com.example.blogapp.core.Result
import com.example.blogapp.data.model.Post
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepository {

    suspend fun getLatestPost() : Result<List<Post>>
    suspend fun registerLikeButtonState(postId: String, liked: Boolean)



}