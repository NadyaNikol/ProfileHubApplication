package com.androiddev.profilehub.domain.useCase

import com.androiddev.profilehub.domain.repository.ContactsRepository
import com.androiddev.profilehub.ui.contacts.event.ContactsEvent
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

/**
 * Created by Nadya N. on 02.06.2025.
 */
class ObserveContactsEventsUseCaseImpl @Inject constructor(
    repository: ContactsRepository,
) : ObserveContactsEventsUseCase {
    override val eventsFlow: SharedFlow<ContactsEvent> = repository.eventsFlow
}