package ru.quipy.logic

import ru.quipy.api.*
import java.util.*


// Commands : takes something -> returns event
// Here the commands are represented by extension functions, but also can be the class member functions

fun ProjectAggregateState.create(id: UUID, title: String, creatorId: String): ProjectCreatedEvent {
    return ProjectCreatedEvent(
        projectId = id,
        title = title,
        creatorId = creatorId,
    )
}

fun ProjectAggregateState.addTask(name: String): TaskCreatedEvent {
    return TaskCreatedEvent(projectId = this.getId(), taskId = UUID.randomUUID(), taskName = name)
}

fun ProjectAggregateState.createTag(name: String): TagCreatedEvent {
    if (projectTags.values.any { it.name == name }) {
        throw IllegalArgumentException("Tag already exists: $name")
    }
    return TagCreatedEvent(projectId = this.getId(), tagId = UUID.randomUUID(), tagName = name)
}

fun ProjectAggregateState.assignTagToTask(tagId: UUID, taskId: UUID): TagAssignedToTaskEvent {
    if (!projectTags.containsKey(tagId)) {
        throw IllegalArgumentException("Tag doesn't exists: $tagId")
    }

    if (!tasks.containsKey(taskId)) {
        throw IllegalArgumentException("Task doesn't exists: $taskId")
    }

    return TagAssignedToTaskEvent(projectId = this.getId(), tagId = tagId, taskId = taskId)
}

fun ProjectAggregateState.inviteUser(issuerUsername: String, newUsername: String): ProjectInvitedEvent {
    if (!this.membersUsername.contains(issuerUsername)) {
        throw IllegalArgumentException("User with username $issuerUsername is not member of this project")
    }

    if (this.membersUsername.contains(newUsername)) {
        throw IllegalArgumentException("User with username $issuerUsername is already member of this project")
    }

    return ProjectInvitedEvent(projectId = this.getId(), issuerUsername = issuerUsername, newUsername = newUsername)
}

fun ProjectAggregateState.changeTitle(newTitle: String, issuerUsername: String): ProjectChangedTitleEvent {
    if (!this.membersUsername.contains(issuerUsername)) {
        throw IllegalArgumentException("User with username $issuerUsername is not member of this project")
    }

    return ProjectChangedTitleEvent(newTitle, issuerUsername)
}

fun ProjectAggregateState.assignTaskToUser(issuerUsername: String, taskId: UUID, assignedUsername: String)
    : TaskAssignedToUserEvent{
    if (!this.membersUsername.contains(issuerUsername)) {
        throw IllegalArgumentException("User with username $issuerUsername is not member of this project")
    }

    if (!this.tasks.containsKey(taskId)) {
        throw IllegalArgumentException("Task with id $taskId is not exist in this project")
    }

    if (!this.membersUsername.contains(assignedUsername)) {
        throw IllegalArgumentException("User with username $assignedUsername is not member of this project")
    }

    return TaskAssignedToUserEvent(issuerUsername, taskId, assignedUsername)
}

fun ProjectAggregateState.createStatus(stateName: String, statusId: UUID, color: String): StatusCreatedEvent{
    if (this.projectStatuses.containsKey(statusId)) {
        throw IllegalArgumentException("Status with id $statusId is already exists in this project")
    }

    return StatusCreatedEvent(stateName, statusId, color)
}

fun ProjectAggregateState.removeStatus(statusId: UUID): StatusRemovedEvent {
    if (!this.projectStatuses.containsKey(statusId)) {
        throw IllegalArgumentException("Status with id $statusId is not exists in this project")
    }

    return StatusRemovedEvent(statusId)
}

fun ProjectAggregateState.changeTaskName(taskId: UUID, newName: String, issuerUsername: String,): TaskChangedNameEvent {
    if (!this.tasks.containsKey(taskId)) {
        throw IllegalArgumentException("Task with id $taskId is not exist in this project")
    }

    if (!this.membersUsername.contains(issuerUsername)) {
        throw IllegalArgumentException("User with username $issuerUsername is not member of this project")
    }

    return TaskChangedNameEvent(taskId, newName, issuerUsername)
}

fun ProjectAggregateState.changeTaskStatus(taskId: UUID, statusId: UUID, issuerUsername: String,): TaskChangedStatusEvent {
    if (!this.tasks.containsKey(taskId)) {
        throw IllegalArgumentException("Task with id $taskId is not exist in this project")
    }

    if (!this.membersUsername.contains(issuerUsername)) {
        throw IllegalArgumentException("User with username $issuerUsername is not member of this project")
    }

    if (!this.projectStatuses.containsKey(statusId)) {
        throw IllegalArgumentException("Status with id $statusId is not exists in this project")
    }


    return TaskChangedStatusEvent(taskId, statusId, issuerUsername)
}

