package com.androiddev.profilehub.di

import com.androiddev.profilehub.domain.useCase.ValidationAddContactUseCase
import com.androiddev.profilehub.domain.useCase.ValidationAddContactUseCaseImpl
import com.androiddev.profilehub.domain.useCase.ValidationAuthUseCase
import com.androiddev.profilehub.domain.useCase.ValidationAuthUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Nadya N. on 23.04.2025.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseBindsModule {

    @Binds
    abstract fun bindValidationAuthUseCase(
        impl: ValidationAuthUseCaseImpl,
    ): ValidationAuthUseCase

    @Binds
    abstract fun bindValidationAddContactUseCase(
        impl: ValidationAddContactUseCaseImpl,
    ): ValidationAddContactUseCase

}