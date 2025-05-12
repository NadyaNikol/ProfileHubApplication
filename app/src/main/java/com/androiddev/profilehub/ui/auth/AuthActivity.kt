package com.androiddev.profilehub.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.androiddev.profilehub.databinding.ActivityAuthBinding
import com.androiddev.profilehub.ui.BaseActivity
import com.androiddev.profilehub.ui.auth.events.AuthFormEvent
import com.androiddev.profilehub.ui.auth.viewModels.AuthViewModel
import com.androiddev.profilehub.ui.main.MainActivity
import com.androiddev.profilehub.utils.EmailParser
import com.androiddev.profilehub.utils.IntentKeys.EXTRA_USER_NAME
import com.androiddev.profilehub.utils.mappers.AuthErrorMessageResolver
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
    private lateinit var authErrorMessageResolver: AuthErrorMessageResolver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authErrorMessageResolver = AuthErrorMessageResolver(this)
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

    private fun setUpTextChangedListener(editText: EditText, event: (String) -> AuthFormEvent) {
        editText.doAfterTextChanged { editable ->
            viewModel.onEvent(event(editable.toString()))
        }
    }

    private fun initObserves() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.onEach { state ->
                    binding.apply {
                        textInputLayoutEmail.helperText =
                            authErrorMessageResolver.resolve(state.emailError)
                        textInputLayoutPassword.helperText =
                            authErrorMessageResolver.resolve(state.passwordError)
                        groupProgressBar.isGone = !state.isLoading

                        if (editTextEmailAddress.text.toString() != state.email) {
                            editTextEmailAddress.setText(state.email)
                        }

                        if (editTextPassword.text.toString() != state.password) {
                            editTextPassword.setText(state.password)
                        }

                        if (checkBoxRememberMe.isChecked != state.isRememberMe) {
                            checkBoxRememberMe.isChecked = state.isRememberMe
                        }

                        if (state.submitDataEvent != null) {
                            val intent = newIntentToMain(
                                this@AuthActivity,
                                EmailParser.extractName(binding.editTextEmailAddress.text.toString())
                            )
                            startActivity(intent)
                        }
                    }
                }.launchIn(this)
            }
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

