package com.example.nlapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private const val CRYPTO_URL = "https://api.coingecko.com/"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    val retrofitCrypto: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(CRYPTO_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val getUsersInfo: ApiInterface by lazy {
        retrofitCrypto.create(ApiInterface::class.java)
    }

}
