package com.example.nlapp.ui.admin

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nlapp.adapters.AdminAdapter
import com.example.nlapp.databinding.AdminFragmentBinding
import com.example.nlapp.ui.base.BaseFragment
import kotlinx.coroutines.launch

class AdminFragment : BaseFragment<AdminFragmentBinding>(AdminFragmentBinding::inflate) {

    private val viewModel: AdminViewModel by viewModels()

    private val adminAdapter by lazy {
        AdminAdapter()
    }

    override fun start() {

        listeners()
        setUpRecycler()

    }

    private fun setUpRecycler() {

        viewModel.getAdminData()
        binding.rvAdminRecycler.layoutManager = LinearLayoutManager(context)
        binding.rvAdminRecycler.adapter = adminAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.adminFlow.collect {
                    adminAdapter.setData(it.toList())
                }
            }
        }
    }

    private fun listeners() {
        adminAdapter.adminItemClicked = {
            it.uid?.let { it1 -> viewModel.deleteUser(it1) }
        }
        binding.tvLogOut.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }
}