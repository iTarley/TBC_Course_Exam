package com.example.nlapp.ui.crypto

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.nlapp.MainActivity
import com.example.nlapp.adapters.CryptoAdapter
import com.example.nlapp.databinding.CryptoFragmentBinding
import com.example.nlapp.model.CryptoDataItem
import com.example.nlapp.ui.base.BaseFragment
import com.example.nlapp.utils.ResponseHandler
import kotlinx.coroutines.launch

class CryptoFragment : BaseFragment<CryptoFragmentBinding>(CryptoFragmentBinding::inflate) {

    private val viewModel: CryptoViewModel by viewModels()
    private val cryptoAdapter by lazy {
        CryptoAdapter()
    }

    override fun start() {
        val activity = requireActivity() as? MainActivity
        activity?.showNavBar()

        setUpRecycler()
        viewModel.getCryptoData()
        observer()
    }


    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cryptoDataFlow.collect {
                    when (it) {
                        is ResponseHandler.Success -> {
                            cryptoAdapter.setData(it.data!!)
                            binding.progressBar.visibility = View.INVISIBLE
                            filterInit(it.data)
                            binding.searchEditText.text?.clear()

                        }

                        is ResponseHandler.Failure -> {
                            Toast.makeText(context, "${it.errorMessage}", Toast.LENGTH_SHORT).show()
                        }

                        is ResponseHandler.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setUpRecycler() {
        binding.cryptoRecycler.adapter = cryptoAdapter
        cryptoAdapter.clickCryptoItem = {
            findNavController().navigate(
                CryptoFragmentDirections.actionCryptoFragmentToCryptoItemFragment(
                    cryptoItem = it
                )
            )
        }
    }

    private fun filterInit(data: List<CryptoDataItem>) {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filter(p0.toString(), data)
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun filter(text: String, data: List<CryptoDataItem>) {


        val filteredCrypto = ArrayList<CryptoDataItem>()

        data.filterTo(filteredCrypto) { item ->
            item.name?.lowercase()?.contains(text.lowercase()) ?: true
        }

        cryptoAdapter.setData(filteredCrypto)


    }
}