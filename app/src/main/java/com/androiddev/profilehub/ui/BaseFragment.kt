package com.androiddev.profilehub.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Created by Nadya N. on 12.07.2025.
 */
abstract class BaseFragment<B : ViewBinding>(
    private val createViewBinding: (
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean,
    ) -> B,
) : Fragment() {

    private var _binding: B? = null
    protected val binding: B
        get() = _binding
            ?: throw RuntimeException("Binding for ${this::class.java.simpleName} is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this::class.java.simpleName, "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = createViewBinding(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        Log.d(this::class.java.simpleName, "onDestroyView: ")
        super.onDestroyView()
    }
}