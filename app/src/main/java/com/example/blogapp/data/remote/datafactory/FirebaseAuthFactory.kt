package com.example.blogapp.data.remote.datafactory

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthFactory {

    companion object {
        val firebaseAuth by lazy {
            FirebaseAuth.getInstance()
        }
    }
}