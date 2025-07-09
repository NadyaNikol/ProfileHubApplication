package com.androiddev.profilehub.domain.useCase

import com.androiddev.profilehub.domain.repository.ContactsRepository
import javax.inject.Inject

/**
 * Created by Nadya N. on 13.05.2025.
 */
interface DeleteContactUseCase {
    suspend operator fun invoke(id: Long)
}

class DeleteContactUseCaseImpl @Inject constructor(
    private val repository: ContactsRepository,
) : DeleteContactUseCase {
    override suspend fun invoke(id: Long) = repository.deleteContactById(id)
}