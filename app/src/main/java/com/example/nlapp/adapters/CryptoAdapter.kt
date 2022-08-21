package com.example.nlapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nlapp.databinding.CryptoItemBinding
import com.example.nlapp.extensions.setImage
import com.example.nlapp.model.CryptoDataItem

class CryptoAdapter :
    ListAdapter<CryptoDataItem, CryptoAdapter.CryptoViewHolder>(CryptoDiffCallBack()) {

    private var content = listOf<CryptoDataItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        return CryptoViewHolder(
            CryptoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.onBind()
    }


    inner class CryptoViewHolder(private val binding: CryptoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {

            val currentItem: CryptoDataItem = content[adapterPosition]

            binding.apply {
                ivCryptoImage.setImage(currentItem.image)
                tvCryptoName.text = currentItem.name
                tvCryptoPrice.text = currentItem.currentPrice.toString()
                tvCryptoSymbol.text = currentItem.symbol
            }
        }
    }

    fun setData(newList: List<CryptoDataItem>){
        content = newList
        submitList(content)
    }

    class CryptoDiffCallBack : DiffUtil.ItemCallback<CryptoDataItem>() {
        override fun areItemsTheSame(oldItem: CryptoDataItem, newItem: CryptoDataItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CryptoDataItem, newItem: CryptoDataItem): Boolean {
            return oldItem == newItem
        }

    }

}
