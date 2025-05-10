package com.androiddev.profilehub.di

import android.content.Context
import com.androiddev.profilehub.utils.mappers.AuthErrorMessageResolver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Nadya N. on 10.05.2025.
 */

@Module
@InstallIn(SingletonComponent::class)
class InfrastructureModule {

    @Provides
    @Singleton
    fun provideAuthErrorMessageResolver(
        @ApplicationContext context: Context,
    ): AuthErrorMessageResolver = AuthErrorMessageResolver(context)
}