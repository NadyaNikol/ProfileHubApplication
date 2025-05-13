package com.androiddev.profilehub.ui.imageLoader

import android.view.View
import android.widget.ImageView
import com.androiddev.profilehub.R
import com.bumptech.glide.Glide
import javax.inject.Inject

/**
 * Created by Nadya N. on 13.05.2025.
 */
class GlideImageLoader @Inject constructor() : ImageLoader {
    override fun load(view: View, url: String, target: ImageView) {
        Glide.with(view)
            .load(url)
            .error(R.drawable.person_icon)
            .into(target)
    }
}