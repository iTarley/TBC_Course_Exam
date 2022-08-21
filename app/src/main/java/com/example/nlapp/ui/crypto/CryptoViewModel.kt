package com.example.nlapp.ui.crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nlapp.model.CryptoDataItem
import com.example.nlapp.network.RetrofitClient
import com.example.nlapp.utils.ResponseHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {

    private var _cryptoDataFlow =
        MutableStateFlow<ResponseHandler<List<CryptoDataItem>>>(ResponseHandler.Loading())
    var cryptoDataFlow = _cryptoDataFlow.asStateFlow()

    fun getCryptoData() {
        viewModelScope.launch {
            val response = RetrofitClient.getUsersInfo.getCryptoData()
            if (response.isSuccessful && response.body() != null) {
                _cryptoDataFlow.emit(ResponseHandler.Success(response.body()))
            } else {
                _cryptoDataFlow.emit(ResponseHandler.Failure(response.errorBody().toString()))
            }
        }
    }
}