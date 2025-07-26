package com.androiddev.profilehub.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.androiddev.profilehub.R
import com.androiddev.profilehub.databinding.ActivityMainBinding
import com.androiddev.profilehub.ui.auth.fragment.SignUpFragment
import com.androiddev.profilehub.ui.main.viewModel.SharedMainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Nadya N. on 08.05.2025.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    internal lateinit var binding: ActivityMainBinding
    private val viewModel: SharedMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        applyInsets()
        handleToolbarBackPress()

        setupUserName()
        setupToolbarVisibilityWithNavController()
    }

    private fun setupUserName() {
        val userName = intent.getStringExtra(SignUpFragment.Companion.EXTRA_USER_NAME) ?: ""
        viewModel.setUserName(userName)
    }

    private fun setupToolbarVisibilityWithNavController() {
        val navController = (supportFragmentManager
            .findFragmentById(R.id.mainNavHostFragment) as NavHostFragment)
            .navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.contactsFragment,
                R.id.contactsDetailViewFragment,
                    -> {
                    binding.toolBarContacts.visibility = View.VISIBLE
                }

                else -> {
                    binding.toolBarContacts.visibility = View.GONE
                }
            }
        }
    }

    private fun handleToolbarBackPress() {
        binding.toolBarContacts.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun applyInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->

            val statusBars = insets.getInsets(WindowInsetsCompat.Type.statusBars())

            binding.toolBarContacts.setPadding(
                binding.toolBarContacts.paddingLeft,
                statusBars.top,
                binding.toolBarContacts.paddingRight,
                binding.toolBarContacts.paddingBottom
            )

            WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
                false

            insets
        }
    }

}