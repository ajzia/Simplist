package com.ajzia.simplist.firebase

import com.ajzia.simplist.model.Category
import com.ajzia.simplist.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@Singleton
class FirestoreRepository @Inject constructor(
  private val firestore: FirebaseFirestore
) {

  fun getCategoriesFlow(): Flow<List<Category>> =
    callbackFlow {
      val listenerRegistration = firestore
        .collection("categories")
        .addSnapshotListener { snapshot, error ->
          if (error != null) {
            println("Error fetching categories: ${error.message}")
            return@addSnapshotListener
          }

          if (snapshot != null) {
            val categories = snapshot.toObjects(Category:: class.java)
            trySend(categories)
          }
        }

      awaitClose {
        listenerRegistration.remove()
      }
    }

}