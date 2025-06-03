package com.androiddev.profilehub.utils

import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import com.androiddev.profilehub.R
import com.bumptech.glide.Glide

/**
 * Created by Nadya N. on 19.05.2025.
 */

fun EditText.updateIfDifferent(newText: String) {
    if (text.toString() != newText) {
        setText(newText)
    }
}

fun CheckBox.updateIfDifferent(newChecked: Boolean) {
    if (isChecked != newChecked) {
        isChecked = newChecked
    }
}

fun ImageView.loadImage(view: View, url: String) {
    Glide.with(view)
        .load(url)
        .error(R.drawable.person_icon)
        .into(this)
}

