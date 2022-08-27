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

    val db: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("User")

    val auth: FirebaseAuth = FirebaseAuth.getInstance()


    private var _adminFlow = MutableSharedFlow<List<User>>()
    var adminFlow = _adminFlow.asSharedFlow()

    private val userList = mutableListOf<User>()

    fun getAdminData() {

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        userList.add(user!!)
                    }
                }
                viewModelScope.launch {
                    _adminFlow.emit(userList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun deleteUser(uid:String) {
        viewModelScope.launch {

            db.child(uid).removeValue()
            auth.currentUser?.delete()
            _adminFlow.emit(userList)
        }

    }
}