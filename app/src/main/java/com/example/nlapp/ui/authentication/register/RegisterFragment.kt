package com.example.nlapp.ui.authentication.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.nlapp.R
import com.example.nlapp.databinding.FragmentRegisterBinding
import com.example.nlapp.model.User
import com.example.nlapp.utils.Validator.isEmailEmpty
import com.example.nlapp.utils.Validator.isEmailValid
import com.example.nlapp.utils.Validator.isNameEmpty
import com.example.nlapp.utils.Validator.isPasswordEmpty
import com.example.nlapp.utils.Validator.isPasswordMatch
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listeners()

    }

    private fun listeners(){
        binding.backBtn.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.registerBtn.setOnClickListener {
            registration()
        }
    }


    //თუ გაქვს იდეა როგორ გავალამაზოთ ფუნქცია, მითხარი
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
            if (isNameEmpty(name,lastName)){
                nameInputLayout.error = getString(R.string.enter_your_name)
                lastNameInputLayout.error = getString(R.string.enter_your_last_name)
            }else{
                nameInputLayout.error = null
                lastNameInputLayout.error = null
            }
            if (!isPasswordMatch(password,passwordRep)){
                repeatPasswordInputLayout.error = getString(R.string.not_match)
            }else{
                repeatPasswordInputLayout.error = null
            }

            if (!isEmailEmpty(email) &&
                !isPasswordEmpty(password) &&
                !isNameEmpty(name,lastName) &&
                isPasswordMatch(password,passwordRep) &&
                isEmailValid(email))
            {
                firebaseAuth(email,password)
            }
        }

    }

    private fun firebaseAuth(email: String, password: String) {
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Snackbar.make(requireView(), getString(R.string.success_reg), Snackbar.LENGTH_LONG).show()
                    addToDb()
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                }else{
                    Snackbar.make(requireView(), getString(R.string.short_password), Snackbar.LENGTH_LONG).show()
                }
            }
    }

    private fun addToDb() {
        val db = FirebaseDatabase.getInstance().getReference("User")
        val auth = FirebaseAuth.getInstance()

        val name = binding.nameEditText.text.toString()
        val lastName = binding.lastNameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val userInfo = User(name, lastName,email)
        db.child(auth.currentUser?.uid!!)
            .setValue(userInfo)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}