package com.example.nlapp.ui.favorite_item

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nlapp.MainActivity
import com.example.nlapp.databinding.FragmentFavoriteCryptoBinding
import com.example.nlapp.R
import com.example.nlapp.extensions.setImage
import com.example.nlapp.ui.base.BaseFragment
import com.example.nlapp.utils.FirebaseConnection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FavoriteCryptoFragment :
    BaseFragment<FragmentFavoriteCryptoBinding>(FragmentFavoriteCryptoBinding::inflate) {


    private val args: FavoriteCryptoFragmentArgs by navArgs()

    override fun start() {
        val activity = requireActivity() as? MainActivity
        activity?.hideNavBar()
        setCryptoItemData()
        listeners()
    }

    private fun listeners(){
        binding.vBack.setOnClickListener {
            findNavController().popBackStack()
            val activity = requireActivity() as? MainActivity
            activity?.showNavBar()
        }
        binding.btnRemoveFromFavorite.setOnClickListener {
            removeFromFavorites()
            findNavController().popBackStack()
            val activity = requireActivity() as? MainActivity
            activity?.showNavBar()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCryptoItemData() {
        binding.apply {
            ivCryptoImage.setImage(args.favoriteCryptoItem.image)
            tvCryptoName.text = args.favoriteCryptoItem.name
            tvCryptoPrice.text = "${tvCryptoPrice.text}" + args.favoriteCryptoItem.currentPrice.toString() + "$"
            tvCryptoHigh.text = "${tvCryptoHigh.text}" + args.favoriteCryptoItem.high24h.toString() + "$"
            tvCryptoLow.text = "${tvCryptoLow.text}" + args.favoriteCryptoItem.low24h.toString() + "$"
            tvCryptoLastUpdate.text = "${tvCryptoLastUpdate.text}" + (args.favoriteCryptoItem.lastUpdated?.substring(0, 10) ?: "")
            tvCryptoRank.text = "${tvCryptoRank.text}" + args.favoriteCryptoItem.marketCapRank.toString()
            tvCryptoPriceChange.text = "${tvCryptoPriceChange.text}" + args.favoriteCryptoItem.priceChange24h.toString() + "$"
        }
    }


    private fun removeFromFavorites() {

        args.favoriteCryptoItem.name?.let {
            FirebaseConnection.db.child(FirebaseConnection.auth.currentUser?.uid!!).child("Favorite").child(it)
                .removeValue()
        }
    }
}