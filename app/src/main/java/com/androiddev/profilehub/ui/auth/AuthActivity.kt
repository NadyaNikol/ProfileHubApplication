package com.androiddev.profilehub.ui.auth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.androiddev.profilehub.R
import com.androiddev.profilehub.databinding.ActivityAuthBinding
import com.androiddev.profilehub.ui.auth.viewModels.AuthViewModel
import com.androiddev.profilehub.utils.customSnackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Nadya N. on 06.04.2025.
 */
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

            viewModel.apply {

                editTextEmailAddress.doOnTextChanged { text, _, _, _ ->
                    if (text?.isNotBlank() == true) {
                        onEvent(AuthFormEvent.EmailChanged(text.toString()))
                    }
                }

                editTextPassword.doOnTextChanged { text, _, _, _ ->
                    if (text?.isNotBlank() == true) {
                        onEvent(AuthFormEvent.PasswordChanged(text.toString()))
                    }
                }

                btnRegister.setOnClickListener {
                    onEvent(AuthFormEvent.Submit)
                }
            }
        }
    }

    private fun initObserves() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                binding.apply {
                    textInputLayoutEmail.helperText = state.emailError
                    textInputLayoutPassword.helperText = state.passwordError
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.authEvent.collectLatest { event ->

                    when (event) {
                        is AuthViewModel.AuthEvent.Loading -> {
                            binding.groupProgressBar.isVisible = true
                        }

                        is AuthViewModel.AuthEvent.Success -> {
                            binding.groupProgressBar.isVisible = false

//                            val intentToMain =
//                                Intent(this@AuthActivity, MainActivity::class.java)
//                                    .apply {
//                                        putExtra(
//                                            "user_email",
//                                            EmailParser.extractName(binding.editTextEmailAddress.text.toString())
//                                        )
//                                    }
//                            startActivity(intentToMain)
                        }

                        is AuthViewModel.AuthEvent.Error -> {
                            binding.apply {
                                groupProgressBar.isVisible = false
                                root.customSnackbar(this@AuthActivity, event.errorMessage)
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }
}

