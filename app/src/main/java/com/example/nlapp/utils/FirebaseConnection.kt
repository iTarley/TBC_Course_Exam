package com.example.nlapp.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object FirebaseConnection {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseDatabase.getInstance().getReference("User")
    val profile = db.child(auth.currentUser?.uid!!).get()
}