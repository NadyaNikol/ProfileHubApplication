package com.androiddev.profilehub.ui.main.home.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.androiddev.profilehub.R
import com.androiddev.profilehub.databinding.FragmentMainBinding
import com.androiddev.profilehub.ui.BaseFragment
import com.androiddev.profilehub.ui.main.viewModel.SharedMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Nadya N. on 21.07.2025.
 */

@AndroidEntryPoint
class MainFragment @Inject constructor() :
    BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val sharedUserViewModel: SharedMainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        setupData()

        initObserves()
    }

    private fun initObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                sharedUserViewModel.state.collect { state ->
                    binding.tvNameProfile.text = state.userName
                }
            }
        }
    }

    private fun setupData() {
        binding.apply {
            ivPhotoProfile.setImageResource(R.drawable.user_photo)
        }
    }

    private fun initListeners() {
        binding.btnViewContacts.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToContactsFragment()
            )
        }
    }

}