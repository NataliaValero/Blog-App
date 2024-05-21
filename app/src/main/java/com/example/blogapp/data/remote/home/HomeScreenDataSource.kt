package com.example.blogapp.data.remote.home

import com.example.blogapp.core.Result
import com.example.blogapp.data.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class HomeScreenDataSource(private val firestore: FirebaseFirestore, private val firebaseAuth: FirebaseAuth) {

    suspend fun getLatestPost(): Result.Success<MutableList<Post>> {

        val postList = mutableListOf<Post>()


        withContext(Dispatchers.IO) {

            val documents =
                firestore.collection("posts").orderBy("createdAt", Query.Direction.DESCENDING)
                    .get()
                    .await()

            documents.forEach { document ->

                // Update id per each document
                val documentRefId = document.id
                document.reference.update("postId", documentRefId)

                // crear data del documento a un objecto Post
                document.toObject(Post::class.java)?.let { post ->

                    post.apply {

                        // Actualizar fecha de publicación
                        // tiempo estimado que le toma al servidor al ir a buscar los post
                        createdAt = document.getTimestamp(
                            "createdAt",
                            DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                        )?.toDate()

                        // agregar id al post
                        postId = documentRefId


                        // Actualizar atributo liked en el post
                        if (isPostLiked(postId, firebaseAuth.currentUser?.uid.toString())) {
                            liked = true
                        } else {
                            liked = false
                        }


                        // agregar post a la lista
                        postList.add(post)
                    }
                }
            }
        }



        return Result.Success(postList)
    }


    suspend fun registerLikeButtonState(postId: String, liked: Boolean) {

        val increment = FieldValue.increment(1)
        val decrement = FieldValue.increment(-1)


        // Current user
        val uid = firebaseAuth.currentUser?.uid

        // Post Ref
        val postRef = firestore.collection("posts").document(postId)


        // Save likes into firestore collection "posts_likes" using post id as document id
        val postsLikesRef = firestore.collection("posts_likes")
            .document(postId)

        // Run transaction
        firestore.runTransaction { transaction ->

            val snapshot = transaction.get(postRef)
            // Get current likes of post
            val likesCount = snapshot.getLong("likes")

            likesCount?.let {

                if (likesCount >= 0) {

                    // If liked is true, add uid to likes array
                    if (liked) {
                        // Check if this post already exists in posts_likes collection
                        if (transaction.get(postsLikesRef).exists()) {
                            // If exists, add uid to likes array
                            transaction.update(postsLikesRef, "likes", FieldValue.arrayUnion(uid))
                        } else {
                            // If not exists, create new document with uid
                            transaction.set(
                                postsLikesRef,
                                mapOf("likes" to listOf(uid)),
                                SetOptions.merge()
                            )

                        }

                        // Update likes in posts collection
                        transaction.update(postRef, "likes", increment)
                    } else {
                        // decrement likes from posts collection
                        transaction.update(postRef, "likes", decrement)
                        // If liked is false, remove uid from likes array
                        transaction.update(postsLikesRef, "likes", FieldValue.arrayRemove(uid))
                    }
                }
            }
        }.addOnFailureListener {
            throw Exception(it.message)
        }

    }

    private suspend fun isPostLiked(postId: String, uid: String): Boolean {

        val post = firestore.collection("posts_likes").document(postId).get().await()

        // Check if post exists
        if (!post.exists()) return false

        // Get likes array
        val likeArray: List<String> = post.get("likes") as List<String>

        // Check if user already liked the post
        return likeArray.contains(uid)
    }


}