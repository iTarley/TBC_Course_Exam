package com.example.nlapp.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.nlapp.MainActivity
import com.example.nlapp.R
import com.example.nlapp.databinding.ProfileFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
    }

    private fun listeners() {
        binding.logOut.setOnClickListener {
            logOut()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}