package com.androiddev.profilehub.domain.useCases

/**
 * Created by Nadya N. on 13.05.2025.
 */
interface DeleteContactUseCase {
    suspend fun deleteContactById(id: Long)
}