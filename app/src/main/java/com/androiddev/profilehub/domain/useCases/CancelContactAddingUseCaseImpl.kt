package com.androiddev.profilehub.domain.useCases

import com.androiddev.profilehub.domain.repositories.ContactsRepository

/**
 * Created by Nadya N. on 03.06.2025.
 */
class CancelContactAddingUseCaseImpl(
    private val repository: ContactsRepository,
) : CancelContactAddingUseCase {
    override suspend fun cancelAdd() = repository.emitCancelContactSaved()
}