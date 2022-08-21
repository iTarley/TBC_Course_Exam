package com.example.nlapp.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.nlapp.R


fun ImageView.setImage(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(context).load(url).placeholder(R.drawable.ic_crypto).error(R.drawable.ic_crypto)
            .into(this)
    } else {
        setImageResource(R.drawable.ic_crypto)
    }
}