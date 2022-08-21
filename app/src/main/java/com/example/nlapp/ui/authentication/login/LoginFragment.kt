package com.example.nlapp.ui.authentication.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.nlapp.R
import com.example.nlapp.databinding.FragmentLoginBinding
import com.example.nlapp.utils.Validator.isEmailEmpty
import com.example.nlapp.utils.Validator.isEmailValid
import com.example.nlapp.utils.Validator.isPasswordEmpty
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val activity = requireActivity() as? MainActivity
//        activity?.hideNavBar()

        //test
        binding.loginBtn.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCryptoFragment())
        }

        listeners()
//        checkUser()





    }

    private fun listeners(){
        binding.registerBtn.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.forgetPassText.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRecoveryFragment())
        }

        binding.loginBtn.setOnClickListener {
            login()

        }
        binding.privacyText.setOnClickListener {
            redirect()
        }
    }

    private fun login() {
        binding.apply {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (isEmailEmpty(email) || !isEmailValid(email)) {
                emailInputLayout.error = getString(R.string.enter_your_email)
            }
            else {
                emailInputLayout.error = null
            }
            if (isPasswordEmpty(password)) {
                passwordInputLayout.error = getString(R.string.enter_correct_password)
            }
            else { 
                passwordInputLayout.error = null

            }
            if (!isEmailEmpty(email) && !isPasswordEmpty(password)) {
                authentication(email, password)

            }
        }

    }

//    private fun checkUser(){
//        if(FirebaseAuth.getInstance().currentUser !=null){
//            navigate()
//        }
//    }

    private fun authentication(email: String, password: String) {
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    navigate()
                }

                else {
                    binding.emailInputLayout.error = getString(R.string.not_match)
                }
            }
    }


    private fun navigate() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCryptoFragment())
    }

    private fun redirect() {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url)))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}