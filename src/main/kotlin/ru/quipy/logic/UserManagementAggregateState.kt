package ru.quipy.logic

import ru.quipy.api.ProjectCreatedEvent
import ru.quipy.api.UserChangedNameEvent
import ru.quipy.api.UserManagementAggregate
import ru.quipy.api.UserRegisteredEvent
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

class UserManagementAggregateState : AggregateState<String, UserManagementAggregate>{
    private lateinit var username: String
    private lateinit var fullname: String
    private lateinit var password: String

    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    override fun getId(): String = username

    @StateTransitionFunc
    fun userRegisteredApply(event: UserRegisteredEvent) {
        username = event.username
        fullname = event.fullname
        password = event.password
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun userChangedNameApply(event: UserChangedNameEvent) {
        username = event.newFullname
        updatedAt = createdAt
    }

}