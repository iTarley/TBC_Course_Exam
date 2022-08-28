package com.example.nlapp.ui.settings

import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.nlapp.MainActivity
import com.example.nlapp.R
import com.example.nlapp.databinding.FragmentSettingsBinding
import com.example.nlapp.ui.base.BaseFragment
import com.example.nlapp.utils.FirebaseConnection
import com.example.nlapp.utils.Validator
import com.google.android.material.snackbar.Snackbar

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override fun start() {
        val activity = requireActivity() as? MainActivity
        activity?.hideNavBar()

        binding.saveBtn2.setOnClickListener {
            passwordRecovery()
        }
        binding.backBtn.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }



    }

    private fun passwordRecovery() {
        val newPass = binding.passwordEditText.text.toString()
        val confirmNewPass = binding.repeatPasswordEditText.text.toString()

        if (Validator.isPasswordEmpty(newPass)) {
            binding.passwordEditText.error =
                getString(R.string.enter_correct_password)
        } else {
            binding.passwordInputLayout.error = null
        }
        if (!Validator.isPasswordMatch(newPass, confirmNewPass)) {
            binding.repeatPasswordInputLayout.error = getString(R.string.not_match)
        } else {
            binding.repeatPasswordInputLayout.error = null
        }

        if(!Validator.isPasswordEmpty(newPass)&& Validator.isPasswordMatch(newPass,confirmNewPass)){

            FirebaseConnection.auth.currentUser?.updatePassword(confirmNewPass)?.addOnCompleteListener {

                if (it.isSuccessful){
                    findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToProfileFragment())
                    val activity = requireActivity() as? MainActivity
                    activity?.showNavBar()
                    Snackbar.make(
                        requireView(),
                        getString(R.string.psChanged),
                        Snackbar.LENGTH_SHORT
                    ).show()

                }else{
                    Snackbar.make(
                        requireView(),
                        getString(R.string.short_password),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }


    }


}