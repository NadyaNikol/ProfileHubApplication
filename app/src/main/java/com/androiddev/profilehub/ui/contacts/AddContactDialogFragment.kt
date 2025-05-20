package com.androiddev.profilehub.ui.contacts

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.androiddev.profilehub.databinding.DialogFragmentAddContactBinding
import com.androiddev.profilehub.domain.entities.ContactUIEntity
import com.androiddev.profilehub.ui.contacts.events.ContactEvent
import com.androiddev.profilehub.ui.contacts.viewModels.ContactViewModel
import kotlin.random.Random

/**
 * Created by Nadya N. on 20.05.2025.
 */
class AddContactDialogFragment : DialogFragment() {

    private val viewModel by activityViewModels<ContactViewModel>()

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            imgBtnAddPhotoProfile.setOnClickListener {
                pickImageLauncher.launch("image/*")
            }

            btnSaveContact.setOnClickListener {
                val name = editTextName.text.toString().trim()
                val career = editTextCareer.text.toString().trim()

                viewModel.onEvent(
                    ContactEvent.SaveContact(
                        ContactUIEntity(
                            id = Random.nextLong(),
                            name = name,
                            career = career,
                            image = selectedPhotoUri?.toString().orEmpty()
                        )
                    )
                )
                dismiss()
            }

            btnCancelAddContact.setOnClickListener {
                viewModel.onEvent(ContactEvent.CancelSaveContact)
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {
        const val ADD_CONTACT_DIALOG_TAG = "AddContactDialog"
    }

}