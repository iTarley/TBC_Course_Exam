package com.example.nlapp.model


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoDataItem(

    @Json(name = "current_price")
    val currentPrice: Double? = 0.0,

    @Json(name = "high_24h")
    val high24h: Double? = 0.0,

    @Json(name = "image")
    val image: String? = "",

    @Json(name = "last_updated")
    val lastUpdated: String? = "",

    @Json(name = "low_24h")
    val low24h: Double? = 0.0,

    @Json(name = "market_cap_rank")
    val marketCapRank: Int? = 0,

    @Json(name = "name")
    val name: String? = "",

    @Json(name = "price_change_24h")
    val priceChange24h: Double? = 0.0,

    @Json(name = "symbol")
    val symbol: String? = "",

    ) : Parcelable