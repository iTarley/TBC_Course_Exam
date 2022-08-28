package com.example.nlapp.ui.profile

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nlapp.model.CryptoDataItem
import com.example.nlapp.model.User
import com.example.nlapp.utils.FirebaseConnection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private var _cryptoFlow = MutableSharedFlow<Set<CryptoDataItem>>()
    var cryptoFlow = _cryptoFlow.asSharedFlow()

    private val cryptoList = mutableSetOf<CryptoDataItem>()

    fun getFavoritesData() {

        FirebaseConnection.db.child(FirebaseConnection.auth.currentUser?.uid!!).child("Favorite")
            .addChildEventListener(object : ChildEventListener {


                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    if (snapshot.exists()) {
                        snapshot.getValue(CryptoDataItem::class.java)?.let { cryptoList.add(it) }
                        viewModelScope.launch {
                            _cryptoFlow.emit(cryptoList)
                        }
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    cryptoList.remove(snapshot.getValue(CryptoDataItem::class.java))
                    viewModelScope.launch {
                        _cryptoFlow.emit(cryptoList)
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
}