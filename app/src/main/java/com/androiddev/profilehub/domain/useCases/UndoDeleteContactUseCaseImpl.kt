package com.androiddev.profilehub.domain.useCases

import com.androiddev.profilehub.domain.repositories.ContactsRepository
import javax.inject.Inject

/**
 * Created by Nadya N. on 13.05.2025.
 */
class UndoDeleteContactUseCaseImpl @Inject constructor(
    private val repository: ContactsRepository,
) : UndoDeleteContactUseCase {
    override suspend fun undoDelete() = repository.undoDelete()
}