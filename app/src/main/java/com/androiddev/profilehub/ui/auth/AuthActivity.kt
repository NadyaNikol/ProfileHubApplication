package com.androiddev.profilehub.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.androiddev.profilehub.R
import com.androiddev.profilehub.databinding.ActivityAuthBinding
import com.androiddev.profilehub.ui.auth.events.AuthEvent
import com.androiddev.profilehub.ui.auth.events.AuthFormEvent
import com.androiddev.profilehub.ui.auth.viewModels.AuthViewModel
import com.androiddev.profilehub.ui.main.MainActivity
import com.androiddev.profilehub.utils.EmailParser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Nadya N. on 06.04.2025.
 */
@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.auth)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initListeners()
        initObserves()
    }

    private fun initListeners() {
        binding.apply {

            with(viewModel) {

                editTextEmailAddress.doAfterTextChanged { editable  ->

                    if (!editable.isNullOrBlank()) {
                        onEvent(AuthFormEvent.EmailChanged(editable.toString()))
                    }
                }

                editTextPassword.doAfterTextChanged { editable ->
                    if (!editable.isNullOrBlank()) {
                        onEvent(AuthFormEvent.PasswordChanged(editable.toString()))
                    }
                }

                checkBoxRememberMe.setOnCheckedChangeListener { _, isChecked ->
                    onEvent(AuthFormEvent.RememberMeChanged(isChecked))
                }

                btnRegister.setOnClickListener {
                    onEvent(AuthFormEvent.Submit)
                }
            }
        }
    }

    private fun initObserves() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.onEach { state ->
                    binding.apply {
                        textInputLayoutEmail.helperText = state.emailError
                        textInputLayoutPassword.helperText = state.passwordError
                        binding.groupProgressBar.isGone = !state.isLoading
                    }
                }.launchIn(this)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.authEvent.onEach { event ->

                    when (event) {
                        is AuthEvent.NavigateToMain -> {

                            val intentToMain =
                                Intent(this@AuthActivity, MainActivity::class.java)
                                    .apply {
                                        putExtra(
                                            "user_name",
                                            EmailParser.extractName(binding.editTextEmailAddress.text.toString())
                                        )
                                    }
                            startActivity(intentToMain)
                        }

                        is AuthEvent.FillSavedCredentials -> {
                            binding.apply {
                                editTextEmailAddress.setText(event.email)
                                editTextPassword.setText(event.password)
                                checkBoxRememberMe.isChecked = event.rememberMe
                            }
                        }
                    }


//                        is AuthEvent.Error -> {
//                            binding.apply {
//                                root.customSnackbar(this@AuthActivity, event.errorMessage)
//                                    .show()
//                            }
//                        }
                }.launchIn(this)
            }
        }
    }
}

