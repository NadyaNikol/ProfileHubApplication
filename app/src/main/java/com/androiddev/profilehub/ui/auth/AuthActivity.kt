package com.androiddev.profilehub.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.androiddev.profilehub.databinding.ActivityAuthBinding
import com.androiddev.profilehub.ui.BaseActivity
import com.androiddev.profilehub.ui.auth.events.AuthFormEvent
import com.androiddev.profilehub.ui.auth.viewModels.AuthViewModel
import com.androiddev.profilehub.ui.main.MainActivity
import com.androiddev.profilehub.utils.EXTRA_USER_NAME
import com.androiddev.profilehub.utils.EmailParser
import com.androiddev.profilehub.utils.UIMessageResolver
import com.androiddev.profilehub.utils.setAfterTextChangedListener
import com.androiddev.profilehub.utils.updateIfDifferent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Nadya N. on 06.04.2025.
 */
@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var messageResolver: UIMessageResolver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        messageResolver = UIMessageResolver(this)
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.onEach { state ->
                    showFieldErrors(state)
                    toggleLoading(state)
                    fillUiFromStoredData(state)
                    handleSubmitData(state)
                }.launchIn(this)
            }
        }
    }

    private fun handleSubmitData(state: AuthState) {
        if (state.submitDataEvent != null) {
            val intent = newIntentToMain(
                this@AuthActivity,
                EmailParser.extractName(binding.editTextEmailAddress.text.toString())
            )
            startActivity(intent)
        }
    }

    private fun toggleLoading(state: AuthState) = with(binding) {
        groupProgressBar?.isGone = !state.isLoading
    }

    private fun showFieldErrors(state: AuthState) = with(binding) {
        textInputLayoutEmail.helperText =
            messageResolver.resolveAuthError(state.emailError)
        textInputLayoutPassword.helperText =
            messageResolver.resolveAuthError(state.passwordError)
    }

    private fun fillUiFromStoredData(state: AuthState) {
        binding.apply {
            editTextEmailAddress.updateIfDifferent(state.email)
            editTextPassword.updateIfDifferent(state.password)
            checkBoxRememberMe.updateIfDifferent(state.isRememberMe)
        }
    }


    companion object {
        fun newIntentToMain(context: Context, userName: String): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_USER_NAME, userName)
            }
        }
    }

}

