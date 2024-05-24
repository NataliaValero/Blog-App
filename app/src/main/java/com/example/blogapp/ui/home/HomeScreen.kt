package com.example.blogapp.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.blogapp.core.Result
import com.example.blogapp.data.model.Post
import com.example.blogapp.presentation.home.HomeScreenViewModel
import com.example.blogapp.ui.components.PostItemComponent

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {

    // lista de post de view model a un lazy column de Post Item Component


    val posts by viewModel.fetchData.observeAsState()

    when(posts) {
        is Result.Loading ->{

        }
        is Result.Success -> {
            val data = (posts as Result.Success).data
            LatestPost(latestPost = data)
            Log.d("Fetching latest post", "${data.size}")
        }

        is Result.Failure -> {
            val exception = (posts as Result.Failure).exception
            Log.e("Error fetching latest post", "${exception.message}")
        }
        else ->{

        }
    }
}

@Composable
fun LatestPost(latestPost: List<Post>) {

    LazyColumn(
        modifier = Modifier.padding(all =10.dp)
    ) {

        items(latestPost) { post ->
            PostItemComponent(post = post)
        }
    }

}