package com.example.bhuvana.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseTest {

    fun testConnection() {

        val db = FirebaseFirestore.getInstance()

        val data = hashMapOf(
            "test" to "connected"
        )

        db.collection("test_connection")
            .add(data)
            .addOnSuccessListener {
                Log.d("FIREBASE_TEST", "SUCCESS Firebase Connected ✔")
            }
            .addOnFailureListener {
                Log.e("FIREBASE_TEST", "FAILED Firebase Not Connected ❌")
            }
    }
}