package com.androiddev.profilehub.domain.useCase

import com.androiddev.profilehub.domain.repository.ContactsRepository
import javax.inject.Inject

/**
 * Created by Nadya N. on 13.05.2025.
 */
interface UndoDeleteContactUseCase {
    suspend operator fun invoke()
}

class UndoDeleteContactUseCaseImpl @Inject constructor(
    private val repository: ContactsRepository,
) : UndoDeleteContactUseCase {
    override suspend fun invoke() = repository.undoDelete()
}