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

        val userName: String = intent.getStringExtra(EXTRA_USER_NAME).orEmpty()
        binding.tvNameProfile.text = userName
        binding.ivPhotoProfile.setImageResource(R.drawable.user_photo)
    }
}