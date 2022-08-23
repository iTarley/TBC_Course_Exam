package com.example.nlapp.ui.exchange

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.nlapp.R
import com.example.nlapp.databinding.ExchangeFragmentBinding
import com.example.nlapp.utils.ResponseHandler
import kotlinx.coroutines.launch

class ExchangeFragment : Fragment() {

    private val viewModel: ExchangeViewModel by viewModels()
    private var _binding: ExchangeFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ExchangeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val currencyList = listOf("USD", "GEL", "EUR", "GBP")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_popup_window_item, currencyList)
        binding.fromCurrency.setAdapter(adapter)
        binding.toCurrency.setAdapter(adapter)

        binding.amountEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.amountEditText.text.toString().isNotEmpty()) {
                    currExchange()
                }else {
                    binding.resultInputText.setText("")
                }

            }
        })

    }


    private fun currExchange() {
        val amount = binding.amountEditText.text.toString().toLong()
        val from = binding.fromCurrency.text.toString()
        val to = binding.toCurrency.text.toString()


        viewModel.getCurrency(amount, from, to)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.exchangeFlow.collect {
                    when (it) {
                        is ResponseHandler.Failure -> Log.d("error", "${it.errorMessage}")
                        is ResponseHandler.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is ResponseHandler.Success -> {
                            binding.resultInputText.setText(getString(R.string.resultExchange, it.data?.value.toString(), to))
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}