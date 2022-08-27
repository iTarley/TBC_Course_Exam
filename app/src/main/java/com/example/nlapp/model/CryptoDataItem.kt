package com.example.nlapp.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoDataItem(

    @Json(name = "current_price")
    val currentPrice: Double,

    @Json(name = "high_24h")
    val high24h: Double,

    @Json(name = "image")
    val image: String,

    @Json(name = "last_updated")
    val lastUpdated: String,

    @Json(name = "low_24h")
    val low24h: Double,

    @Json(name = "market_cap_rank")
    val marketCapRank: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "price_change_24h")
    val priceChange24h: Double,

    @Json(name = "symbol")
    val symbol: String,

) : Parcelable