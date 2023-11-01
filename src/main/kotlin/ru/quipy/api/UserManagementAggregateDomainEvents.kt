package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event

const val USER_REGISTERED_EVENT = "USER_REGISTERED_EVENT"
const val USER_CHANGED_NAME_EVENT = "USER_CHANGED_NAME_EVENT"

@DomainEvent(name = USER_REGISTERED_EVENT)
class UserRegisteredEvent(
    val username: String,
    val fullname: String,
    val password: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<UserManagementAggregate>(
    name = USER_REGISTERED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = USER_CHANGED_NAME_EVENT)
class UserChangedNameEvent(
    val username: String,
    val newFullname: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<UserManagementAggregate>(
    name = USER_CHANGED_NAME_EVENT,
    createdAt = createdAt,
)