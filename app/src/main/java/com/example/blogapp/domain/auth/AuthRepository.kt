package com.example.blogapp.domain.auth

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun signUp(username: String, email: String, password: String) : FirebaseUser?
    suspend fun signIn(email: String, password: String) : FirebaseUser?
    suspend fun updateProfile(profilePicture: Uri, username: String)
    val currentUser : FirebaseUser?
    fun logOut()
}