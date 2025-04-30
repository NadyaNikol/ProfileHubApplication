package com.androiddev.profilehub.ui.main

import android.os.Bundle
import com.androiddev.profilehub.R
import com.androiddev.profilehub.databinding.ActivityMainBinding
import com.androiddev.profilehub.ui.BaseActivity
import com.androiddev.profilehub.utils.IntentKeys.EXTRA_USER_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}