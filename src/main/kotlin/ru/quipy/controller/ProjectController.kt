package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.api.*
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.*
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>
) {

    @PostMapping("/{projectTitle}")
    fun createProject(@PathVariable projectTitle: String, @RequestParam creatorId: String) : ProjectCreatedEvent {
        return projectEsService.create { it.create(UUID.randomUUID(), projectTitle, creatorId) }
    }

    @GetMapping("/{projectId}")
    fun getAccount(@PathVariable projectId: UUID) : ProjectAggregateState? {
        return projectEsService.getState(projectId)
    }

    @PostMapping("/{projectId}/tasks/{taskName}")
    fun createTask(@PathVariable projectId: UUID, @PathVariable taskName: String) : TaskCreatedEvent {
        return projectEsService.update(projectId) {
            it.addTask(taskName)
        }
    }

    @PostMapping("/{projectId}/invite")
    fun inviteUserToProject(
        @PathVariable projectId: UUID,
        @RequestParam issuerUsername: String,
        @RequestParam newUsername: String
    ): ProjectInvitedEvent {
        return projectEsService.update(projectId) {it.inviteUser(issuerUsername, newUsername) }
    }

    @PutMapping("/{projectId}/title")
    fun changeProjectTitle(
        @PathVariable projectId: UUID,
        @RequestParam newTitle: String,
        @RequestParam issuerUsername: String
    ): ProjectChangedTitleEvent {
        return projectEsService.update(projectId) { it.changeTitle(newTitle, issuerUsername) }
    }

    @PostMapping("/{projectId}/task/{taskId}/assign")
    fun assignTaskToUser(
        @PathVariable projectId: UUID,
        @PathVariable taskId: UUID,
        @RequestParam issuerUsername: String,
        @RequestParam assignedUsername: String
    ): TaskAssignedToUserEvent {
        return projectEsService.update(projectId) { it.assignTaskToUser(issuerUsername, taskId, assignedUsername) }
    }

    @PostMapping("/{projectId}/status")
    fun createStatus(
        @PathVariable projectId: UUID,
        @RequestParam stateName: String,
        @RequestParam statusId: UUID,
        @RequestParam color: String
    ): StatusCreatedEvent {
        return projectEsService.update(projectId) { it.createStatus(stateName, statusId, color) }
    }

    @DeleteMapping("/{projectId}/status/{statusId}")
    fun removeStatus(
        @PathVariable projectId: UUID,
        @PathVariable statusId: UUID
    ): StatusRemovedEvent {
        return projectEsService.update(projectId) { it.removeStatus(statusId) }
    }

    @PutMapping("/{projectId}/task/{taskId}/name")
    fun changeTaskName(
        @PathVariable projectId: UUID,
        @PathVariable taskId: UUID,
        @RequestParam newName: String,
        @RequestParam issuerUsername: String
    ): TaskChangedNameEvent {
        return projectEsService.update(projectId) { it.changeTaskName(taskId, newName, issuerUsername) }
    }

    @PutMapping("/{projectId}/task/{taskId}/status/{statusId}")
    fun changeTaskStatus(
        @PathVariable projectId: UUID,
        @PathVariable taskId: UUID,
        @PathVariable statusId: UUID,
        @RequestParam issuerUsername: String
    ): TaskChangedStatusEvent {
        return projectEsService.update(projectId) { it.changeTaskStatus(taskId, statusId, issuerUsername) }
    }

}