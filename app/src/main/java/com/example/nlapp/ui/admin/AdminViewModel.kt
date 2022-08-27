package com.example.nlapp.ui.admin

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nlapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AdminViewModel : ViewModel() {

    val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("User")

    val auth: FirebaseAuth = FirebaseAuth.getInstance()


    private var _adminFlow = MutableStateFlow<List<User>>(emptyList())
    var adminFlow = _adminFlow.asStateFlow()

    private val userList = mutableListOf<User>()

    fun getAdminData() {

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        userList.add(user!!)
                    }

                    viewModelScope.launch {
                        _adminFlow.emit(userList)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun deleteUser(userUID: String) {
        databaseReference.child(userUID).removeValue()
        auth.currentUser?.delete()
    }
}