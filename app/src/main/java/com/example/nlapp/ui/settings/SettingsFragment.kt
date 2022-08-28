package com.example.nlapp.ui.settings

import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nlapp.MainActivity
import com.example.nlapp.R
import com.example.nlapp.databinding.FragmentSettingsBinding
import com.example.nlapp.ui.base.BaseFragment
import com.example.nlapp.utils.FirebaseConnection
import com.example.nlapp.utils.Validator
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override fun start() {
        val activity = requireActivity() as? MainActivity
        activity?.hideNavBar()

        binding.saveBtn.setOnClickListener {
            infoSetup()
        }
        binding.backBtn.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

    }



    private fun infoSetup(){


        binding.apply {

            val newPass = passwordEditText.text.toString()
            val confirmNewPass = repeatPasswordEditText.text.toString()


            if (Validator.isPasswordEmpty(newPass)) {
                passwordInputLayout.error =
                    getString(R.string.enter_correct_password)
            } else {
                passwordInputLayout.error = null
            }
            if (!Validator.isPasswordMatch(newPass, confirmNewPass)) {
                repeatPasswordInputLayout.error = getString(R.string.not_match)
            } else {
                repeatPasswordInputLayout.error = null
            }
            if (!Validator.isPasswordEmpty(newPass) && Validator.isPasswordMatch(newPass, confirmNewPass)){
               FirebaseConnection.auth.currentUser?.updatePassword(confirmNewPass)
                    ?.addOnCompleteListener { task ->

                        if (activity == null) return@addOnCompleteListener

                        if (task.isSuccessful){
                            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToProfileFragment())
                            val activity = requireActivity() as? MainActivity
                            activity?.showNavBar()
                            Snackbar.make(
                                requireView(),
                                getString(R.string.psChanged),
                                Snackbar.LENGTH_LONG
                            ).show()

                        }else{
                            Snackbar.make(
                                requireView(),
                                getString(R.string.short_password),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }

                    }
            }
        }
    }



}