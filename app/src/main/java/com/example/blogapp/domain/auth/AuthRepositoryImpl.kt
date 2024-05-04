package com.example.blogapp.domain.auth

import android.graphics.Bitmap
import android.net.Uri
import com.example.blogapp.data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepositoryImpl(private val dataSource: AuthDataSource): AuthRepository {
    override suspend fun signUp(username: String, email: String, password: String): FirebaseUser? {
        return dataSource.signUp(username, email, password)
    }

    override suspend fun signIn(email: String, password: String): FirebaseUser? {
        return dataSource.signIn(email, password)
    }

    override suspend fun updateProfile(profilePicture: Uri, username: String) {
        dataSource.updateUserProfile(profilePicture, username)
    }

    override val currentUser: FirebaseUser?
        get() = dataSource.currentUser

    override fun logOut() {
        dataSource.logout()
    }
}