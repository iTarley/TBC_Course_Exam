package com.example.nlapp.ui.profile

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
import com.example.nlapp.model.User
import com.example.nlapp.ui.base.BaseFragment
import com.example.nlapp.utils.FirebaseConnection
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    private val profileAdapter by lazy {
        CryptoAdapter()
    }

    override fun start() {
        val activity = requireActivity() as? MainActivity
        activity?.showNavBar()
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
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment())
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
