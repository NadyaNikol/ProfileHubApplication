package com.androiddev.profilehub.ui.main.home.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.androiddev.profilehub.R
import com.androiddev.profilehub.databinding.FragmentMainBinding
import com.androiddev.profilehub.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Nadya N. on 21.07.2025.
 */

@AndroidEntryPoint
class MainFragment @Inject constructor() :
    BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        setupData()
    }

    private fun setupData() {
        val userName = MainFragmentArgs.fromBundle(requireArguments()).userName
        binding.apply {
            tvNameProfile.text = userName
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