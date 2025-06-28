package com.androiddev.profilehub.di

import android.content.Context
import com.androiddev.profilehub.util.UIMessageResolver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

/**
 * Created by Nadya N. on 30.06.2025.
 */
@Module
@InstallIn(ActivityComponent::class)
object ActivityScopeModule {

    @Provides
    fun provideUIMessageResolver(
        @ActivityContext context: Context
    ): UIMessageResolver = UIMessageResolver(context)
}
