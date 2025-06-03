package com.androiddev.profilehub.di

import com.androiddev.profilehub.domain.repositories.ContactsRepository
import com.androiddev.profilehub.domain.useCases.AddContactUseCase
import com.androiddev.profilehub.domain.useCases.AddContactUseCaseImpl
import com.androiddev.profilehub.domain.useCases.CancelContactAddingUseCase
import com.androiddev.profilehub.domain.useCases.CancelContactAddingUseCaseImpl
import com.androiddev.profilehub.domain.useCases.DeleteContactUseCase
import com.androiddev.profilehub.domain.useCases.DeleteContactUseCaseImpl
import com.androiddev.profilehub.domain.useCases.GetContactsUseCase
import com.androiddev.profilehub.domain.useCases.GetContactsUseCaseImpl
import com.androiddev.profilehub.domain.useCases.ObserveContactsEventsUseCase
import com.androiddev.profilehub.domain.useCases.ObserveContactsEventsUseCaseImpl
import com.androiddev.profilehub.domain.useCases.UndoDeleteContactUseCase
import com.androiddev.profilehub.domain.useCases.UndoDeleteContactUseCaseImpl
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

    @Provides
    @Singleton
    fun provideGetContactsUseCase(repository: ContactsRepository): GetContactsUseCase =
        GetContactsUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideAddContactsUseCase(repository: ContactsRepository): AddContactUseCase =
        AddContactUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideDeleteContactsUseCase(repository: ContactsRepository): DeleteContactUseCase =
        DeleteContactUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideUndoDeleteContactsUseCase(repository: ContactsRepository): UndoDeleteContactUseCase =
        UndoDeleteContactUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideObserveContactsEventsUseCase(repository: ContactsRepository): ObserveContactsEventsUseCase =
        ObserveContactsEventsUseCaseImpl(repository)

}