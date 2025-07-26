package com.androiddev.profilehub.ui.main.contacts.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.androiddev.profilehub.R
import com.androiddev.profilehub.databinding.FragmentContactsDetailViewBinding
import com.androiddev.profilehub.domain.entity.ContactUIEntity
import com.androiddev.profilehub.ui.BaseFragment
import com.androiddev.profilehub.ui.main.contacts.ContactDetailUIState
import com.androiddev.profilehub.ui.main.MainActivity
import com.androiddev.profilehub.ui.main.contacts.viewModel.ContactDetailViewModel
import com.androiddev.profilehub.util.extension.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Nadya N. on 12.07.2025.
 */

@AndroidEntryPoint
class ContactsDetailViewFragment : BaseFragment<FragmentContactsDetailViewBinding>(
    FragmentContactsDetailViewBinding::inflate
) {

    private val viewModel: ContactDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserves()
        initToolbar()
    }

    private fun initToolbar() {
        val toolbar = (requireActivity() as MainActivity).binding.toolBarContacts

        toolbar.title = getString(R.string.tool_bar_profile_title)
        toolbar.menu.clear()
    }

    private fun initObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->

                    renderLoadingOrNoData(state)
                    renderContact(state.contact)
                }
            }
        }
    }

    private fun renderLoadingOrNoData(state: ContactDetailUIState) {
        binding.apply {
            progressBarLoadData.isVisible = state.isLoading
            groupSomethingWentWrong.isVisible = !state.isLoading && state.isNotFound
        }
    }

    private fun renderContact(contact: ContactUIEntity?) {
        if (contact == null) return

        binding.apply {
            tvNameProfile.text = contact.name
            tvCareerProfile.text = contact.career
            ivPhotoProfile.loadImage(requireView(), contact.image)
        }
    }
}