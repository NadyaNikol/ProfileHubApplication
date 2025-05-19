package com.androiddev.profilehub.utils.mappers

import android.widget.CheckBox
import android.widget.EditText

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

