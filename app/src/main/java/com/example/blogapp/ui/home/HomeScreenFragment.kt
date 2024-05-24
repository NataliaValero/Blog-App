package com.example.blogapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.material3.MaterialTheme
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.blogapp.R
import com.example.blogapp.core.Result
import com.example.blogapp.core.hide
import com.example.blogapp.core.show
import com.example.blogapp.data.model.Post
import com.example.blogapp.data.remote.home.HomeScreenDataSource
import com.example.blogapp.databinding.FragmentHomeScreenBinding
import com.example.blogapp.domain.home.HomeScreenRepositoryImpl
import com.example.blogapp.presentation.home.HomeScreenViewModel
import com.example.blogapp.presentation.home.HomeScreenViewModelFactory
import com.example.blogapp.ui.home.adapter.HomeScreenAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen),
    HomeScreenAdapter.OnPostClickListener {


    private lateinit var binding: FragmentHomeScreenBinding
    private var registration: ListenerRegistration? = null


    private val viewModel: HomeScreenViewModel by activityViewModels {
        HomeScreenViewModelFactory(
            HomeScreenRepositoryImpl(
                HomeScreenDataSource(
                    FirebaseFirestore.getInstance(),
                    FirebaseAuth.getInstance()
                )
            )
        )
    }


    private lateinit var adapter: HomeScreenAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)
        adapter = HomeScreenAdapter(emptyList(), this)

        showHomeScreen()

//
//        viewModel.fetchLatestPosts().observe(viewLifecycleOwner, Observer { result ->
//
//
//            when (result) {
//                is Result.Loading -> {
//                    binding.progressBar.show()
//                }
//
//                is Result.Success -> {
//                    binding.progressBar.hide()
//
//                    adapter.setNewList(result.data)
//
//                    if (result.data.isEmpty()) {
//                        binding.emptyContainer.show()
//                        return@Observer
//                    } else {
//                        binding.emptyContainer.hide()
//                    }
//
//                    binding.rvHome.adapter = adapter
//                    Log.d("POST LIST", result.data.toString())
//
//                }
//
//                is Result.Failure -> {
//                    binding.progressBar.hide()
//                    Toast.makeText(
//                        requireContext(),
//                        " Ocurrió un error: ${result.exception}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    Log.e("ERROR", "ERROR: ${result.exception}")
//                }
//            }
//        })


    }

    override fun onLikeButtonClick(post: Post, isLiked: Boolean) {

        //viewModel.likePost(post, isLiked)
        viewModel.registerLikeButtonState(post.postId, isLiked).observe(viewLifecycleOwner) {

            when (it) {
                is Result.Loading -> {
                }

                is Result.Success -> {

                }

                is Result.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        " Ocurrió un error: ${it.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("ERROR", "ERROR: ${it.exception}")
                }
            }
        }
    }

    private fun showHomeScreen() {

        binding.lazyColumHome.setContent {

            MaterialTheme {
                HomeScreen(viewModel)
            }

        }
    }

}