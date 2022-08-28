package com.example.nlapp.ui.authentication.register

import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.nlapp.R
import com.example.nlapp.databinding.FragmentRegisterBinding
import com.example.nlapp.model.User
import com.example.nlapp.ui.base.BaseFragment
import com.example.nlapp.utils.FirebaseConnection
import com.example.nlapp.utils.Validator.isEmailEmpty
import com.example.nlapp.utils.Validator.isEmailValid
import com.example.nlapp.utils.Validator.isNameEmpty
import com.example.nlapp.utils.Validator.isPasswordEmpty
import com.example.nlapp.utils.Validator.isPasswordMatch
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    override fun start() {
        listeners()
    }


    private fun listeners() {
        binding.backBtn.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.registerBtn.setOnClickListener {
            registration()
        }
    }


    private fun registration() {
        binding.apply {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val passwordRep = repeatPasswordEditText.text.toString()
            val name = nameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()


            if (isEmailEmpty(email) || !isEmailValid(email)) {
                emailInputLayout.error = getString(R.string.enter_your_email)
            } else {
                emailInputLayout.error = null
            }

            if (isPasswordEmpty(password)) {
                passwordInputLayout.error =
                    getString(R.string.enter_correct_password)
            } else {
                passwordInputLayout.error = null
            }
            if (isNameEmpty(name, lastName)) {
                nameInputLayout.error = getString(R.string.enter_your_name)
                lastNameInputLayout.error = getString(R.string.enter_your_last_name)
            } else {
                nameInputLayout.error = null
                lastNameInputLayout.error = null
            }
            if (!isPasswordMatch(password, passwordRep)) {
                repeatPasswordInputLayout.error = getString(R.string.not_match)
            } else {
                repeatPasswordInputLayout.error = null
            }

            if (!isEmailEmpty(email) &&
                !isPasswordEmpty(password) &&
                !isNameEmpty(name, lastName) &&
                isPasswordMatch(password, passwordRep) &&
                isEmailValid(email)
            ) {
                firebaseAuth(email, password)
            }
        }

    }

    private fun firebaseAuth(email: String, password: String) {
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.success_reg),
                        Snackbar.LENGTH_LONG
                    ).show()
                    addToDb()
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                } else {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.short_password),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun addToDb() {

        val name = binding.nameEditText.text.toString()
        val lastName = binding.lastNameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val uid = FirebaseConnection.auth.currentUser?.uid!!
        val image = binding.imageEditText.text.toString()
        val userInfo = User(name, lastName, email, uid, image)
        FirebaseConnection.db.child(FirebaseConnection.auth.currentUser?.uid!!)
            .setValue(userInfo)
    }


}