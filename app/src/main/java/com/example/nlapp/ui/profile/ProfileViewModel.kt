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


    private var _cryptoFlow = MutableSharedFlow<CryptoDataItem>()
    var cryptoFlow = _cryptoFlow.asSharedFlow()


    fun getFavoritesData() {

        FirebaseConnection.db.child(FirebaseConnection.auth.currentUser?.uid!!).child("Favorite")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        for (cryptoSnapshot in snapshot.children) {
                            val cryptoItem = cryptoSnapshot.getValue(CryptoDataItem::class.java)
                            viewModelScope.launch {
                                cryptoItem?.let { _cryptoFlow.emit(it) }
                                d("item","$cryptoItem")
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
}