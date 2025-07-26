package com.androiddev.profilehub.domain.useCase

import com.androiddev.profilehub.ui.main.contacts.event.ContactsEvent
import kotlinx.coroutines.flow.SharedFlow

/**
 * Created by Nadya N. on 02.06.2025.
 */
interface ObserveContactsEventsUseCase {
    val eventsFlow: SharedFlow<ContactsEvent>
}