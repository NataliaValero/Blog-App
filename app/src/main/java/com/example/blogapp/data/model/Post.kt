package com.example.blogapp.data.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date


data class Post(
    var postId: String = "",
    val poster: Poster? = null,
    @ServerTimestamp
    var createdAt : Date? = null,
    val postImage : String = "",
    val postDescription: String = "",
    var likes : Long = 0,
    @Exclude @JvmField
    var liked : Boolean = false
)

// Person who editst the post
data class Poster(val userId : String? = "", val profileName: String? = "", val profilePicture: String? = "")