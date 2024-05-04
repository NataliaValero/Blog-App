package com.example.blogapp.data.remote.datafactory

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreFactory {

    companion object{
        val firestore by lazy {
            Firebase.firestore
        }
    }
}