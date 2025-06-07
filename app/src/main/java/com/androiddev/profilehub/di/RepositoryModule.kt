package com.androiddev.profilehub.di

import com.androiddev.profilehub.data.ContactRepositoryLocalImpl
import com.androiddev.profilehub.data.UserPreferencesRepositoryImpl
import com.androiddev.profilehub.domain.repositories.ContactsRepository
import com.androiddev.profilehub.domain.repositories.UserPreferencesRepository
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