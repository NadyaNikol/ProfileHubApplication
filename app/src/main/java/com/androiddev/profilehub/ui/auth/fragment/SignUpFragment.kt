package com.androiddev.profilehub.ui.auth.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.androiddev.profilehub.databinding.FragmentSignUpBinding
import com.androiddev.profilehub.ui.BaseFragment
import com.androiddev.profilehub.ui.auth.AuthUIState
import com.androiddev.profilehub.ui.auth.event.AuthFormEvent
import com.androiddev.profilehub.ui.auth.viewModel.AuthViewModel
import com.androiddev.profilehub.ui.main.MainActivity
import com.androiddev.profilehub.util.EmailParser
import com.androiddev.profilehub.util.UIMessageResolver
import com.androiddev.profilehub.util.extension.setAfterTextChangedListener
import com.androiddev.profilehub.util.extension.updateIfDifferent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Nadya N. on 21.07.2025.
 */

@AndroidEntryPoint
class SignUpFragment @Inject constructor() :
    BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var messageResolver: UIMessageResolver

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initObserves()
    }

    private fun initListeners() {
        binding.apply {
            setUpTextChangedListener(editTextEmailAddress) { editable ->
                AuthFormEvent.EmailChanged(editable)
            }
            setUpTextChangedListener(editTextPassword) { editable ->
                AuthFormEvent.PasswordChanged(editable)
            }

            checkBoxRememberMe.setOnCheckedChangeListener { _, isChecked ->
                viewModel.onEvent(AuthFormEvent.RememberMeChanged(isChecked))
            }

            btnRegister.setOnClickListener {
                viewModel.onEvent(AuthFormEvent.Submit)
            }
        }
    }

    private fun setUpTextChangedListener(
        editText: EditText,
        event: (String) -> AuthFormEvent,
    ) {
        editText.setAfterTextChangedListener(
            event = { event(it) },
            onEvent = viewModel::onEvent
        )
    }

    private fun initObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect { state ->
                    showFieldErrors(state)
                    toggleLoading(state.isLoading)
                    fillUiFromStoredData(state)
                    handleSubmitData(state.submitDataEvent)
                }
            }
        }
    }

    private fun handleSubmitData(submitDataEvent: Unit?) {
        if (submitDataEvent != null) {
            val intent = newIntentToMain(
                requireActivity(),
                EmailParser.extractName(binding.editTextEmailAddress.text.toString())
            )
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun toggleLoading(isLoading: Boolean) = with(binding) {
        groupProgressBar.isVisible = isLoading
    }

    private fun showFieldErrors(state: AuthUIState) = with(binding) {
        textInputLayoutEmail.helperText =
            messageResolver.resolveAuthError(state.emailError)
        textInputLayoutPassword.helperText =
            messageResolver.resolveAuthError(state.passwordError)
    }

    private fun fillUiFromStoredData(state: AuthUIState) {
        binding.apply {
            editTextEmailAddress.updateIfDifferent(state.email)
            editTextPassword.updateIfDifferent(state.password)
            checkBoxRememberMe.updateIfDifferent(state.isRememberMe)
        }
    }


    companion object {
        const val EXTRA_USER_NAME = "userName"

        fun newIntentToMain(context: Context, userName: String): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_USER_NAME, userName)
            }
        }
    }

}