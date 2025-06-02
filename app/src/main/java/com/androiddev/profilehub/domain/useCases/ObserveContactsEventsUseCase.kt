package com.androiddev.profilehub.domain.useCases

import com.androiddev.profilehub.ui.contacts.events.ContactsEvent
import kotlinx.coroutines.flow.SharedFlow

/**
 * Created by Nadya N. on 02.06.2025.
 */
interface ObserveContactsEventsUseCase {
    val eventsFlow: SharedFlow<ContactsEvent>
}