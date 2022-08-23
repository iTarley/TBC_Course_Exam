package com.example.nlapp.ui.cryptoitem

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nlapp.MainActivity
import com.example.nlapp.R
import com.example.nlapp.databinding.CryptoItemFragmentBinding
import com.example.nlapp.extensions.setImage

class CryptoItemFragment : Fragment() {

    private var _binding: CryptoItemFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: CryptoItemFragmentArgs by navArgs()

    private var favorite = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CryptoItemFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as? MainActivity
        activity?.hideNavBar()
        setCryptoItemData()

        listeners()
    }

    private fun listeners() {
        binding.vFavorite.setOnClickListener {
            checkFavorite()
        }
        binding.vBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCryptoItemData() {
        binding.apply {
            ivCryptoImage.setImage(args.cryptoItem.image)
            tvCryptoName.text = args.cryptoItem.name
            tvCryptoPrice.text =
                "${tvCryptoPrice.text}" + args.cryptoItem.currentPrice.toString() + "$"
            tvCryptoHigh.text = "${tvCryptoHigh.text}" + args.cryptoItem.high24h.toString() + "$"
            tvCryptoLow.text = "${tvCryptoLow.text}" + args.cryptoItem.low24h.toString() + "$"
            tvCryptoLastUpdate.text =
                "${tvCryptoLastUpdate.text}" + args.cryptoItem.lastUpdated.substring(0, 10)
            tvCryptoRank.text = "${tvCryptoRank.text}" + args.cryptoItem.marketCapRank.toString()
            tvCryptoPriceChange.text =
                "${tvCryptoPriceChange.text}" + args.cryptoItem.priceChange24h.toString() + "$"
        }
    }

    private fun addToFavorites() {
        binding.vFavorite.setBackgroundResource(R.drawable.ic_favorite_true)
        favorite = 1
    }

    private fun removeFromFavorites() {
        binding.vFavorite.setBackgroundResource(R.drawable.ic_favorite_false)
        favorite = 0
    }

    private fun checkFavorite() {
        if (favorite == 0) {
            addToFavorites()
        } else {
            removeFromFavorites()
        }
    }
}