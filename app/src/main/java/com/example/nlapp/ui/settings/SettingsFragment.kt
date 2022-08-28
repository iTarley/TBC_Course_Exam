package com.example.nlapp.ui.settings

import androidx.navigation.fragment.navArgs
import com.example.nlapp.MainActivity
import com.example.nlapp.databinding.FragmentSettingsBinding
import com.example.nlapp.ui.base.BaseFragment

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val args: SettingsFragmentArgs by navArgs()
    override fun start() {
        val activity = requireActivity() as? MainActivity
        activity?.hideNavBar()
        infoSetup()

    }

    private fun infoSetup(){
        binding.nameEditText.setText(args.name)
        binding.lastNameEditText.setText(args.lastName)
    }
}