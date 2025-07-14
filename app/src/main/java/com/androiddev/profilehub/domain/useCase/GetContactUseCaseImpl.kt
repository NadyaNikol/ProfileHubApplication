package com.androiddev.profilehub.domain.useCase

import com.androiddev.profilehub.domain.entity.ContactUIEntity
import com.androiddev.profilehub.domain.repository.ContactsRepository
import javax.inject.Inject

/**
 * Created by Nadya N. on 13.05.2025.
 */
interface GetContactUseCase {
    suspend operator fun invoke(itemId: Long): ContactUIEntity?
}

class GetContactUseCaseImpl @Inject constructor(
    private val repository: ContactsRepository,
) : GetContactUseCase {
    override suspend fun invoke(itemId: Long) = repository.getContactById(itemId)
}