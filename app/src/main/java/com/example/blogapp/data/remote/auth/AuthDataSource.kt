package com.example.blogapp.data.remote.auth
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.blogapp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class AuthDataSource(private val firebaseAuth : FirebaseAuth) {

    private val firebase = FirebaseFirestore.getInstance()
    private val firestorage = FirebaseStorage.getInstance()


    suspend fun signUp(username: String, email:String, password: String) : FirebaseUser? {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()


        authResult.user?.uid.let {
            // Add new user to firebase collection using uid

            firebase.collection("users").document(it.toString()).set(User(email, username)).await()
        }

        return authResult.user
    }

    suspend fun signIn(email:String, password: String) : FirebaseUser? {

        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }

    suspend fun updateUserProfile(profilePic: Uri, username: String) {

        val user = currentUser

        // Save image firestorage using current user id
        val imageRef = firestorage.reference.child("${user?.uid}/profile_picture.jpg")
        // Retorna downlod url from storage
        val downloadUrl = imageRef.putFile(profilePic).await().storage.downloadUrl.await().toString()


        val profileUpdates = userProfileChangeRequest{
            displayName = username
            photoUri = Uri.parse(downloadUrl)
        }

        user?.updateProfile(profileUpdates)?.await()

        // Update profile changes in firebase users collection
        user?.let {
            firebase.collection("users").document(it.uid).update(
                mapOf( "photoUrl" to downloadUrl))
        }

    }

    fun logout() {
        firebaseAuth.signOut()
    }


    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser
}

