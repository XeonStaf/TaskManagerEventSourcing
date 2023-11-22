package ru.quipy.logic

import ru.quipy.api.UserChangedNameEvent
import ru.quipy.api.UserRegisteredEvent

fun UserManagementAggregateState.registerUser(username: String, fullname: String, password: String): UserRegisteredEvent {
   return UserRegisteredEvent(username, fullname, password)
}

fun UserManagementAggregateState.changeUserName(newFullName: String): UserChangedNameEvent {
    if (this.getFullName() == newFullName) {
        throw IllegalArgumentException("User with name: $newFullName already exists")
    }

    return UserChangedNameEvent(this.getId(), newFullName)
}