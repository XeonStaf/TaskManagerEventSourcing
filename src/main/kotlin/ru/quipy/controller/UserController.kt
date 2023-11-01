package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.api.ProjectCreatedEvent
import ru.quipy.api.UserChangedNameEvent
import ru.quipy.api.UserManagementAggregate
import ru.quipy.api.UserRegisteredEvent
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.UserManagementAggregateState
import ru.quipy.logic.changeUserName
import ru.quipy.logic.create
import ru.quipy.logic.registerUser
import java.util.*


@RestController
@RequestMapping("/users")
class UserController(
    val userManagementEsService: EventSourcingService<String, UserManagementAggregate, UserManagementAggregateState>
) {

    @PostMapping()
    fun registerUser(@RequestParam username: String,
                     @RequestParam fullname: String,
                     @RequestParam password: String) : UserRegisteredEvent {
        return userManagementEsService.create { it.registerUser(username, fullname, password) }
    }

    @PutMapping("/username")
    fun changeUserName(@PathVariable username: String, @RequestParam fullname: String) : UserChangedNameEvent{
        return userManagementEsService.update(username) { it.changeUserName(fullname) }
    }

}
