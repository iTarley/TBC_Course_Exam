package com.example.nlapp.ui.authentication.passwordRecovery

import androidx.navigation.Navigation
import com.example.nlapp.R
import com.example.nlapp.databinding.FragmentRecoveryBinding
import com.example.nlapp.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class RecoveryFragment : BaseFragment<FragmentRecoveryBinding>(FragmentRecoveryBinding::inflate) {


    override fun start() {
        listeners()
    }


    private fun listeners() {
        binding.backBtn.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        binding.sendMail.setOnClickListener {
            passwordRecover()
        }
    }

    private fun passwordRecover() {

        FirebaseAuth
            .getInstance()
            .sendPasswordResetEmail(binding.emailEditText.text.toString())
            .addOnCompleteListener { function ->
                if (function.isSuccessful) {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.check_email),
                        Snackbar.LENGTH_LONG
                    )
                        .setAction(getString(R.string.open)) {
                            val launchIntent =
                                requireContext().packageManager.getLaunchIntentForPackage("com.google.android.gm")
                            if (launchIntent != null) {
                                startActivity(launchIntent)
                            }
                        }
                        .show()
                    binding.emailInputLayout.error = null
                } else {
                    binding.emailInputLayout.error = getString(R.string.doent_exits)
                }

            }
    }

}