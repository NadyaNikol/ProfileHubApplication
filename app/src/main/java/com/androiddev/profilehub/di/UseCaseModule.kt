package com.androiddev.profilehub.di

import android.content.Context
import com.androiddev.profilehub.domain.useCases.ValidationEmailUseCase
import com.androiddev.profilehub.domain.useCases.ValidationPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Nadya N. on 23.04.2025.
 */
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideValidationPasswordUseCase(@ApplicationContext context: Context) =
        ValidationPasswordUseCase(context)

    @Provides
    @Singleton
    fun provideValidationEmailUseCase(@ApplicationContext context: Context) =
        ValidationEmailUseCase(context)

}