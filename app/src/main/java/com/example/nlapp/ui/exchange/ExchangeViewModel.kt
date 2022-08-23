package com.example.nlapp.ui.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nlapp.model.CurrencyRate
import com.example.nlapp.network.RetrofitClient
import com.example.nlapp.utils.ResponseHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExchangeViewModel : ViewModel() {

    private var _exchangeFlow = MutableStateFlow<ResponseHandler<CurrencyRate>>(ResponseHandler.Loading())
    val exchangeFlow = _exchangeFlow.asStateFlow()

    fun getCurrency(amount: Long, from:String, to:String){
        viewModelScope.launch {
            val response = RetrofitClient.getCurrencyInfo.getExchangeData(amount,from,to)
            if (response.isSuccessful){
                _exchangeFlow.emit(ResponseHandler.Success(response.body()))
            }else{
                _exchangeFlow.emit(ResponseHandler.Failure(response.errorBody().toString()))
            }
        }
    }
}