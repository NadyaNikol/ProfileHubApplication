package com.androiddev.profilehub.ui.contacts

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.androiddev.profilehub.databinding.ActivityContactsBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Nadya N. on 08.05.2025.
 */

@AndroidEntryPoint
class ContactsActivity : AppCompatActivity() {
    internal lateinit var binding: ActivityContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityContactsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        applyInsets()
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

            WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false

            insets
        }
    }

}