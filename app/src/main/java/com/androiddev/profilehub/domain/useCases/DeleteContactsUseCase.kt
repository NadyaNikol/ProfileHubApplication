package com.androiddev.profilehub.domain.useCases

/**
 * Created by Nadya N. on 13.05.2025.
 */
interface DeleteContactsUseCase {
    suspend fun deleteContactById(id: Long)
    suspend fun undoDelete()
}