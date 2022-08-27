package com.example.nlapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nlapp.databinding.AdminItemBinding
import com.example.nlapp.extensions.setImage
import com.example.nlapp.model.CryptoDataItem
import com.example.nlapp.model.User

class AdminAdapter : ListAdapter<User, AdminAdapter.AdminViewHolder>(CryptoDiffCallBack()) {

    private var usersList = listOf<User>()

    var adminItemClicked: ((User) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminAdapter.AdminViewHolder {
        return AdminViewHolder(
            AdminItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AdminAdapter.AdminViewHolder, position: Int) {
        holder.onBind()
    }

    override fun getItemCount() = usersList.size

    inner class AdminViewHolder(private val binding: AdminItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind() {
            binding.apply {
                tvEmail.text = "${tvEmail.text} : ${usersList[adapterPosition].email}"
                tvName.text = "${tvName.text} : ${usersList[adapterPosition].name}"
                tvLastName.text = "${tvLastName.text} : ${usersList[adapterPosition].lastName}"
                ivUser.setImage(usersList[adapterPosition].image)
                root.setOnClickListener {
                    adminItemClicked?.invoke(getItem(adapterPosition))
                }
            }
        }
    }

    fun setData(data: List<User>) {
        usersList = data
        submitList(usersList)

    }

    class CryptoDiffCallBack : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}