package com.androiddev.profilehub.ui.contacts

/**
 * Created by Nadya N. on 02.06.2025.
 */
sealed class LoadingState {
    data object Idle : LoadingState()
    data object LoadingInitial : LoadingState()
    data object Loaded : LoadingState()
}