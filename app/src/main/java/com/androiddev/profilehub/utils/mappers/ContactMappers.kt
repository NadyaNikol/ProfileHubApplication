package com.androiddev.profilehub.utils.mappers

import android.view.View
import android.widget.ImageView
import com.androiddev.profilehub.R
import com.bumptech.glide.Glide

/**
 * Created by Nadya N. on 13.05.2025.
 */

fun ImageView.loadImage(view: View, url: String) {
    Glide.with(view)
        .load(url)
        .error(R.drawable.person_icon)
        .into(this)
}

