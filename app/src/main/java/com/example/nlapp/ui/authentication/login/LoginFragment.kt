package com.example.nlapp.ui.authentication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nlapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

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


        binding.registerBtn.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.forgetPassText.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRecoveryFragment())
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}