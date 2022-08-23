package com.example.nlapp.network

import com.example.nlapp.model.CryptoDataItem
import com.example.nlapp.model.CurrencyRate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    @GET("/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=50&page=1&sparkline=false/")
    suspend fun getCryptoData(): Response<List<CryptoDataItem>>

    @Headers("apikey: 1M9y5FMfI29t45QkVaeQ0hsEovmqZEXJ")
    @GET("v1/exchange-rates/nbg/convert")
    suspend fun getExchangeData(
        @Query("amount") Amount: Long,
        @Query("from") From: String,
        @Query("to") To: String
    ): Response<CurrencyRate>

}