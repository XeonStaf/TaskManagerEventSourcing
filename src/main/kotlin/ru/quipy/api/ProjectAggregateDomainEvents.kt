package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val PROJECT_CREATED_EVENT = "PROJECT_CREATED_EVENT"
const val PROJECT_INVITED_USER_EVENT = "PROJECT_INVITED_USER_EVENT"
const val TAG_CREATED_EVENT = "TAG_CREATED_EVENT"
const val TAG_ASSIGNED_TO_TASK_EVENT = "TAG_ASSIGNED_TO_TASK_EVENT"
const val TASK_CREATED_EVENT = "TASK_CREATED_EVENT"
const val PROJECT_CHANGED_TITLE_EVENT = "PROJECT_CHANGED_TITLE_EVENT"
const val STATUS_CREATED_EVENT = "STATUS_CREATED_EVENT"
const val TASK_CHANGED_NAME_EVENT = "TASK_CHANGED_NAME_EVENT"
const val STATUS_REMOVED_EVENT = "STATUS_REMOVED_EVENT"
const val TASK_ASSIGNED_TO_USER_EVENT = "TASK_ASSIGNED_TO_USER_EVENT"
const val TASK_CHANGED_STATUS_EVENT = "TASK_CHANGED_STATUS_EVENT"

// API
@DomainEvent(name = PROJECT_CREATED_EVENT)
class ProjectCreatedEvent(
    val projectId: UUID,
    val title: String,
    val creatorId: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PROJECT_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = PROJECT_CHANGED_TITLE_EVENT)
class ProjectChangedTitleEvent(
    val newTitle: String,
    val issuerUsername: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PROJECT_CHANGED_TITLE_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = TASK_ASSIGNED_TO_USER_EVENT)
class TaskAssignedToUserEvent(
    val issuerUsername: String,
    val taskId: UUID,
    val assignedUsername: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = TASK_ASSIGNED_TO_USER_EVENT,
    createdAt = createdAt,
)


@DomainEvent(name = PROJECT_INVITED_USER_EVENT)
class ProjectInvitedEvent(
    val projectId: UUID,
    val issuerUsername: String,
    val newUsername: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PROJECT_CHANGED_TITLE_EVENT,
    createdAt = createdAt,
)


@DomainEvent(name = TAG_CREATED_EVENT)
class TagCreatedEvent(
    val projectId: UUID,
    val tagId: UUID,
    val tagName: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = TAG_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = TASK_CREATED_EVENT)
class TaskCreatedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val taskName: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = TASK_CREATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = TAG_ASSIGNED_TO_TASK_EVENT)
class TagAssignedToTaskEvent(
    val projectId: UUID,
    val taskId: UUID,
    val tagId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = TAG_ASSIGNED_TO_TASK_EVENT,
    createdAt = createdAt
)