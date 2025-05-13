package com.androiddev.profilehub.di

import com.androiddev.profilehub.domain.repositories.ContactRepositoryLocalImpl
import com.androiddev.profilehub.domain.repositories.ContactsRepository
import com.androiddev.profilehub.domain.useCases.GetContactsUseCase
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
class ContactModule {

    @Provides
    @Singleton
    fun provideContactRepository(): ContactsRepository {
        return ContactRepositoryLocalImpl()
    }

    @Provides
    @Singleton
    fun provideGetContactsUseCase(
        repository: ContactsRepository,
    ): GetContactsUseCase {
        return GetContactsUseCase(repository)
    }
}