package com.androiddev.profilehub.ui.main.viewModel

import androidx.lifecycle.ViewModel
import com.androiddev.profilehub.ui.main.home.SharedUserUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Created by Nadya N. on 26.07.2025.
 */

@HiltViewModel
class SharedMainViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(SharedUserUIState())
    val state: StateFlow<SharedUserUIState> = _state.asStateFlow()

    fun setUserName(userName: String) {
        _state.update { it.copy(userName = userName) }
    }
}