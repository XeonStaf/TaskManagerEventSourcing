package ru.quipy.logic

import ru.quipy.api.UserChangedNameEvent
import ru.quipy.api.UserRegisteredEvent

fun UserManagementAggregateState.registerUser(username: String, fullname: String, password: String): UserRegisteredEvent {
   return UserRegisteredEvent(username, fullname, password)
}

fun UserManagementAggregateState.changeUserName(newFullName: String): UserChangedNameEvent {
    return UserChangedNameEvent(this.getId(), newFullName)
}