package com.example.nlapp.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private const val CRYPTO_URL = "https://api.coingecko.com/"

    val retrofitCrypto: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(CRYPTO_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val getUsersInfo: ApiInterface by lazy {
        retrofitCrypto.create(ApiInterface::class.java)
    }

}
