package com.example.nlapp.ui.crypto

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nlapp.MainActivity
import com.example.nlapp.R
import com.example.nlapp.adapters.CryptoAdapter
import com.example.nlapp.databinding.CryptoFragmentBinding
import com.example.nlapp.model.CryptoDataItem
import com.example.nlapp.utils.ResponseHandler
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Response.error
import java.util.Locale.filter

class CryptoFragment : Fragment() {

    private val viewModel: CryptoViewModel by viewModels()

    private var _binding: CryptoFragmentBinding? = null
    private val binding get() = _binding!!

    private val cryptoAdapter by lazy {
        CryptoAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CryptoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as? MainActivity
        activity?.showNavBar()

        setUpRecycler()
        viewModel.getCryptoData()
        observer()
        filterInit()
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cryptoDataFlow.collect {
                    when (it) {
                        is ResponseHandler.Success -> {
                            it.data?.let { data -> cryptoAdapter.setData(data.sortedBy { it.marketCapRank }) }
                            binding.progressBar.visibility = View.INVISIBLE
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
        binding.cryptoRecycler.layoutManager = LinearLayoutManager(context)
        binding.cryptoRecycler.adapter = cryptoAdapter
        cryptoAdapter.submitList(emptyList())

        cryptoAdapter.clickCryptoItem = {
            findNavController().navigate(
                CryptoFragmentDirections.actionCryptoFragmentToCryptoItemFragment(
                    cryptoItem = it
                )
            )
        }
    }

    private fun filterInit(){
        binding.searchEditText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filter(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun filter(text:String){

        val filteredCrypto = ArrayList<CryptoDataItem>()

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.cryptoDataFlow.collect{
                    when(it){
                        is ResponseHandler.Failure -> {
                            Toast.makeText(context, "${it.errorMessage}", Toast.LENGTH_SHORT).show()
                        }
                        is ResponseHandler.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is ResponseHandler.Success -> {
                            it.data?.filterTo(filteredCrypto){item->
                                item.name.lowercase().contains(text.lowercase())
                            }
                            cryptoAdapter.filterList(filteredCrypto)
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }




    }


}