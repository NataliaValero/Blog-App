package com.example.blogapp.domain.camera

import android.net.Uri

interface CameraRepository {

    suspend fun uploadPhoto(imageUrl: Uri, description: String)
}