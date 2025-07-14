package com.androiddev.profilehub.di

import com.androiddev.profilehub.domain.repository.ContactsRepository
import com.androiddev.profilehub.domain.useCase.AddContactUseCase
import com.androiddev.profilehub.domain.useCase.AddContactUseCaseImpl
import com.androiddev.profilehub.domain.useCase.CancelContactAddingUseCase
import com.androiddev.profilehub.domain.useCase.CancelContactAddingUseCaseImpl
import com.androiddev.profilehub.domain.useCase.DeleteContactUseCase
import com.androiddev.profilehub.domain.useCase.DeleteContactUseCaseImpl
import com.androiddev.profilehub.domain.useCase.GetContactUseCase
import com.androiddev.profilehub.domain.useCase.GetContactUseCaseImpl
import com.androiddev.profilehub.domain.useCase.GetContactsUseCase
import com.androiddev.profilehub.domain.useCase.GetContactsUseCaseImpl
import com.androiddev.profilehub.domain.useCase.ObserveContactsEventsUseCase
import com.androiddev.profilehub.domain.useCase.ObserveContactsEventsUseCaseImpl
import com.androiddev.profilehub.domain.useCase.UndoDeleteContactUseCase
import com.androiddev.profilehub.domain.useCase.UndoDeleteContactUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Nadya N. on 23.04.2025.
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseProvideModule {

    @Provides
    fun provideGetContactsUseCase(repository: ContactsRepository): GetContactsUseCase =
        GetContactsUseCaseImpl(repository)

    @Provides
    fun provideAddContactsUseCase(repository: ContactsRepository): AddContactUseCase =
        AddContactUseCaseImpl(repository)

    @Provides
    fun provideDeleteContactsUseCase(repository: ContactsRepository): DeleteContactUseCase =
        DeleteContactUseCaseImpl(repository)

    @Provides
    fun provideUndoDeleteContactsUseCase(repository: ContactsRepository): UndoDeleteContactUseCase =
        UndoDeleteContactUseCaseImpl(repository)

    @Provides
    fun provideObserveContactsEventsUseCase(repository: ContactsRepository): ObserveContactsEventsUseCase =
        ObserveContactsEventsUseCaseImpl(repository)

    @Provides
    fun provideCancelContactAddingUseCase(repository: ContactsRepository): CancelContactAddingUseCase =
        CancelContactAddingUseCaseImpl(repository)

    @Provides
    fun provideGetContactUseCase(repository: ContactsRepository): GetContactUseCase =
        GetContactUseCaseImpl(repository)

}