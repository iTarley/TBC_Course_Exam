package com.example.nlapp.network

import com.example.nlapp.model.CryptoDataItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=50&page=1&sparkline=false/")
    suspend fun getCryptoData(): Response<List<CryptoDataItem>>

}