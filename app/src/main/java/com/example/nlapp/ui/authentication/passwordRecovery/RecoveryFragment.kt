package com.example.nlapp.ui.authentication.passwordRecovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.nlapp.R
import com.example.nlapp.databinding.FragmentRecoveryBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class RecoveryFragment : Fragment() {


    private var _binding: FragmentRecoveryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
            .addOnCompleteListener {function ->
                if (function.isSuccessful){
                    Snackbar.make(requireView(),getString(R.string.check_email), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.open)){
                            val launchIntent = requireContext().packageManager.getLaunchIntentForPackage("com.google.android.gm")
                            if (launchIntent != null) {
                                startActivity(launchIntent)
                            }
                        }
                        .show()
                    binding.emailInputLayout.error = null
                }
                else{
                    binding.emailInputLayout.error = getString(R.string.doent_exits)
                }

            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}