package com.example.nlapp.ui.admin

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nlapp.model.User
import com.example.nlapp.utils.FirebaseConnection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AdminViewModel : ViewModel() {


    private var _adminFlow = MutableSharedFlow<List<User>>()
    var adminFlow = _adminFlow.asSharedFlow()

    private val userList = mutableListOf<User>()

    fun getAdminData() {

        FirebaseConnection.db.addValueEventListener(object : ValueEventListener {
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

            FirebaseConnection.db.child(uid).removeValue()
            FirebaseConnection.auth.currentUser?.delete()
            _adminFlow.emit(userList)
        }

    }
}