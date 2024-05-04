package com.example.blogapp.data.remote.datafactory

import com.google.firebase.storage.FirebaseStorage

class FirestorageFactory {

    companion object {
        val firebaseStorage by lazy {
            FirebaseStorage.getInstance()
        }
    }
}