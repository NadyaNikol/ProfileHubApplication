package com.androiddev.profilehub.ui.contacts

/**
 * Created by Nadya N. on 02.06.2025.
 */
sealed class LoadingState {
    object Idle : LoadingState()
    object LoadingInitial : LoadingState()
    object Loaded : LoadingState()
}