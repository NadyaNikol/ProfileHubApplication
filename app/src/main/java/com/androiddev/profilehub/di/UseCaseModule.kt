package com.androiddev.profilehub.di

import com.androiddev.profilehub.domain.useCases.ValidationEmailUseCase
import com.androiddev.profilehub.domain.useCases.ValidationEmailUseCaseImpl
import com.androiddev.profilehub.domain.useCases.ValidationPasswordUseCase
import com.androiddev.profilehub.domain.useCases.ValidationPasswordUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideValidationPasswordUseCase(): ValidationPasswordUseCase =
        ValidationPasswordUseCaseImpl()

    @Provides
    @Singleton
    fun provideValidationEmailUseCase(): ValidationEmailUseCase =
        ValidationEmailUseCaseImpl()

}