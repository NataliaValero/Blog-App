package com.example.blogapp.data.remote.camera

import android.net.Uri
import com.example.blogapp.data.model.Post
import com.example.blogapp.data.model.Poster
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.UUID

class CameraDataSource(private val firebaseStorage: FirebaseStorage) {

    suspend fun uploadPhoto(imageUrl: Uri, description: String) {

        val user = FirebaseAuth.getInstance().currentUser
        val randomName = UUID.randomUUID().toString()

        // Save photo to firestorage
        val imageRef = firebaseStorage.reference.child("${user?.uid}/posts/$randomName")
        // Retorna url
        val downloadUrl = imageRef.putFile(imageUrl).await().storage.downloadUrl.await().toString()


        // Guardar post en firebasecollection



        user?.let {
            FirebaseFirestore.getInstance().collection("posts")
                .add(
                    Post(
                        postId = "",
                        poster = Poster(it.uid,
                            it.displayName.toString(),
                            it.photoUrl.toString()),
                        createdAt = null,
                        postImage = downloadUrl,
                        postDescription = description
                )
            )
        }



    }
}