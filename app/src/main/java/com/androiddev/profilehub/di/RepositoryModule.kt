package com.androiddev.profilehub.di

import com.androiddev.profilehub.domain.repositories.ContactRepositoryLocalImpl
import com.androiddev.profilehub.domain.repositories.ContactsRepository
import com.androiddev.profilehub.domain.repositories.UserPreferencesRepository
import com.androiddev.profilehub.domain.repositories.UserPreferencesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Nadya N. on 23.04.2025.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        impl: UserPreferencesRepositoryImpl,
    ): UserPreferencesRepository

    @Binds
    @Singleton
    abstract fun bindContactRepository(
        impl: ContactRepositoryLocalImpl,
    ): ContactsRepository
}