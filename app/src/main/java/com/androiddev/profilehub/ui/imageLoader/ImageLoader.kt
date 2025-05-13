package com.androiddev.profilehub.ui.imageLoader

import android.view.View
import android.widget.ImageView

/**
 * Created by Nadya N. on 13.05.2025.
 */
interface ImageLoader {
    fun load(view: View, url: String, target: ImageView)
}