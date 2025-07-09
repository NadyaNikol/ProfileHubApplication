package com.androiddev.profilehub.domain.useCase

import com.androiddev.profilehub.domain.repository.ContactsRepository

/**
 * Created by Nadya N. on 03.06.2025.
 */
interface CancelContactAddingUseCase {
    suspend operator fun invoke()
}

class CancelContactAddingUseCaseImpl(
    private val repository: ContactsRepository,
) : CancelContactAddingUseCase {
    override suspend fun invoke() = repository.emitCancelContactSaved()
}