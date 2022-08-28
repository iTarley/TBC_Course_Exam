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


    private var _adminFlow = MutableSharedFlow<Set<User>>()
    var adminFlow = _adminFlow.asSharedFlow()

    private val userList = mutableSetOf<User>()

    fun getAdminData() {

        FirebaseConnection.db.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    snapshot.getValue(User::class.java)?.let { userList.add(it) }
                }
                viewModelScope.launch {
                    _adminFlow.emit(userList)
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                userList.remove(snapshot.getValue(User::class.java))
                viewModelScope.launch {
                    _adminFlow.emit(userList)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun deleteUser(uid: String) {
        viewModelScope.launch {

            FirebaseConnection.db.child(uid).removeValue()
            FirebaseConnection.auth.currentUser?.delete()
            _adminFlow.emit(userList)
        }

    }
}