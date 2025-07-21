package com.androiddev.profilehub.ui.contacts

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.androiddev.profilehub.R
import com.androiddev.profilehub.databinding.ActivityMainBinding
import com.androiddev.profilehub.ui.auth.fragment.SignUpFragment.Companion.EXTRA_USER_NAME
import com.androiddev.profilehub.ui.main.home.fragment.MainFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Nadya N. on 08.05.2025.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    internal lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        applyInsets()
        handleToolbarBackPress()

        val userName = intent.getStringExtra(EXTRA_USER_NAME) ?: ""

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.main_navigation)

        navGraph.setStartDestination(R.id.mainFragment)

        val args = bundleOf(
            MainFragmentArgs::userName.name to userName
        )

        navGraph.setStartDestination(R.id.mainFragment)
        navController.setGraph(navGraph, args)
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