package com.example.nlapp.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log.d
import android.util.Log.i
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nlapp.MainActivity
import com.example.nlapp.R
import com.example.nlapp.adapters.CryptoAdapter
import com.example.nlapp.databinding.ProfileFragmentBinding
import com.example.nlapp.extensions.setImage
import com.example.nlapp.model.CryptoDataItem
import com.example.nlapp.model.User
import com.example.nlapp.ui.base.BaseFragment
import com.example.nlapp.utils.FirebaseConnection
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class ProfileFragment : BaseFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    private val profileAdapter by lazy {
        CryptoAdapter()
    }

    override fun start() {
        setUpProfile()
        setUpRecycler()
        listeners()
    }


    private fun setUpRecycler() {
        viewModel.getFavoritesData()
        binding.favoritesRecycler.layoutManager = LinearLayoutManager(context)
        binding.favoritesRecycler.adapter = profileAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cryptoFlow.collect {
                    profileAdapter.submitList(it.toList())
                }
            }
        }
    }

    private fun listeners() {
        binding.logOut.setOnClickListener {
            logOut()
        }

        binding.settings.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment(binding.tvName.text.toString(),binding.tvLastName.text.toString()))
        }
        profileAdapter.clickCryptoItem = {

            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToFavoriteCryptoFragment(
                    favoriteCryptoItem = it
                )
            )
        }
    }

    private fun logOut() {
        MaterialAlertDialogBuilder(requireView().context)
            .setTitle(resources.getString(R.string.log_out))
            .setMessage(getString(R.string.sure))

            .setNegativeButton(getString(R.string.decline)) { dialog, which ->

            }
            .setPositiveButton(getString(R.string.accept)) { dialog, which ->
                FirebaseAuth.getInstance().signOut()
                goBack()

            }

            .show()
    }

    private fun goBack() {
        val activity = requireActivity() as? MainActivity
        activity?.hideNavBar()
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
    }

    private fun setUpProfile(){
        FirebaseConnection.profile.addOnSuccessListener {
            val profile = it.getValue(User::class.java)
            binding.ivUserProfile.setImage(profile?.image)
            binding.tvLastName.text = profile?.lastName.toString()
            binding.tvName.text = profile?.name.toString()
        }
    }

}
