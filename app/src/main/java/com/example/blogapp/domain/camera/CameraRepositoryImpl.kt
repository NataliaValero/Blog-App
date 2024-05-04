package com.example.blogapp.domain.camera

import android.net.Uri
import com.example.blogapp.data.remote.camera.CameraDataSource

class CameraRepositoryImpl(private val dataSource: CameraDataSource) : CameraRepository{
    override suspend fun uploadPhoto(imageUrl: Uri, description: String) {
        dataSource.uploadPhoto(imageUrl, description)
    }
}