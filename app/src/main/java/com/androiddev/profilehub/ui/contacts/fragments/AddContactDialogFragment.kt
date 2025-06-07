package com.androiddev.profilehub.ui.contacts.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.androiddev.profilehub.databinding.DialogFragmentAddContactBinding
import com.androiddev.profilehub.domain.entities.ContactUIEntity
import com.androiddev.profilehub.ui.contacts.AddContactsState
import com.androiddev.profilehub.ui.contacts.events.AddContactFormEvent
import com.androiddev.profilehub.ui.contacts.events.ContactDialogEvent
import com.androiddev.profilehub.ui.contacts.viewModels.AddContactDialogViewModel
import com.androiddev.profilehub.utils.UIMessageResolver
import com.androiddev.profilehub.utils.setAfterTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

/**
 * Created by Nadya N. on 20.05.2025.
 */

@AndroidEntryPoint
class AddContactDialogFragment @Inject constructor(
) : DialogFragment() {

    private val viewModel: AddContactDialogViewModel by viewModels()
    private lateinit var messageResolver: UIMessageResolver

    private lateinit var binding: DialogFragmentAddContactBinding
    private var selectedPhotoUri: Uri? = null

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.ivPhotoProfile.setImageURI(it)
                selectedPhotoUri = it
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DialogFragmentAddContactBinding.inflate(inflater, container, false)
        messageResolver = UIMessageResolver(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserves()
        initListeners()
    }

    private fun initListeners() {
        binding.apply {
            setUpTextChangedListener(editTextName) { editable ->
                AddContactFormEvent.NameChanged(editable)
            }
            setUpTextChangedListener(editTextCareer) { editable ->
                AddContactFormEvent.CareerChanged(editable)
            }

            imgBtnAddPhotoProfile.setOnClickListener {
                pickImageLauncher.launch("image/*")
            }

            btnSaveContact.setOnClickListener {
                val name = editTextName.text.toString().trim()
                val career = editTextCareer.text.toString().trim()

                viewModel.onUIEvent(
                    ContactDialogEvent.Add(
                        ContactUIEntity(
                            id = Random.Default.nextLong(),
                            name = name,
                            career = career,
                            image = selectedPhotoUri?.toString().orEmpty()
                        )
                    )
                )
            }

            btnCancelAddContact.setOnClickListener {
                viewModel.onUIEvent(ContactDialogEvent.Cancel)
            }
        }
    }

    private fun initObserves() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.onEach { state ->
                    binding.apply {
                        showFieldErrors(state)
                        handleSubmit(state)
                    }
                }.launchIn(this)
            }
        }
    }

    private fun handleSubmit(state: AddContactsState) {
        if (state.submitDataEvent != null) {
            dismiss()
        }
    }

    private fun showFieldErrors(state: AddContactsState) = with(binding) {
        textInputLayoutName.helperText =
            messageResolver.resolveAddContactError(state.nameError)
        textInputLayoutCareer.helperText =
            messageResolver.resolveAddContactError(state.careerError)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setUpTextChangedListener(
        editText: EditText,
        event: (String) -> AddContactFormEvent,
    ) {
        editText.setAfterTextChangedListener(
            event = { event(it) },
            onEvent = viewModel::onEvent
        )
    }
}