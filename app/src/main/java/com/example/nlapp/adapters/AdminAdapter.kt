package com.example.nlapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nlapp.databinding.AdminItemBinding
import com.example.nlapp.extensions.setImage
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

    inner class AdminViewHolder(private val binding: AdminItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var currentItem: User

        @SuppressLint("SetTextI18n")
        fun onBind() {

            currentItem = getItem(adapterPosition)

            binding.apply {
                tvEmail.text = "${tvEmail.text} : ${currentItem.email}"
                tvName.text = "${tvName.text} : ${currentItem.name}"
                tvLastName.text = "${tvLastName.text} : ${currentItem.lastName}"
                ivUser.setImage(currentItem.image)

                root.setOnClickListener {
                    adminItemClicked?.invoke(currentItem)
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