package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

// Service's business logic
class ProjectAggregateState : AggregateState<UUID, ProjectAggregate> {
    private lateinit var projectId: UUID
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    lateinit var projectTitle: String
    lateinit var creatorId: String
    var tasks = mutableMapOf<UUID, TaskEntity>()
    var projectTags = mutableMapOf<UUID, TagEntity>()
    var projectStatuses = mutableMapOf<UUID, StatusEntity>()
    var membersUsername = mutableSetOf<String>()

    override fun getId() = projectId

    // State transition functions which is represented by the class member function
    @StateTransitionFunc
    fun projectCreatedApply(event: ProjectCreatedEvent) {
        projectId = event.projectId
        projectTitle = event.title
        creatorId = event.creatorId
        updatedAt = createdAt
    }


    @StateTransitionFunc
    fun tagCreatedApply(event: TagCreatedEvent) {
        projectTags[event.tagId] = TagEntity(event.tagId, event.tagName)
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun taskCreatedApply(event: TaskCreatedEvent) {
        tasks[event.taskId] = TaskEntity(event.taskId,
                    event.taskName, mutableSetOf(), null, null)
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun changedTitleApply(event: ProjectChangedTitleEvent) {
        if (membersUsername.contains(event.issuerUsername)) {
            projectTitle = event.newTitle;
            updatedAt = createdAt
        }
    }

    @StateTransitionFunc
    fun userInvitedInProjectApply(event: ProjectInvitedEvent) {
        if (membersUsername.contains(event.issuerUsername)) {
            membersUsername.add(event.newUsername)
            updatedAt = createdAt
        }
    }

    @StateTransitionFunc
    fun assignedUserToTaskApply(event: TaskAssignedToUserEvent) {
        if (membersUsername.contains(event.issuerUsername) && tasks.containsKey(event.taskId)) {
            tasks[event.taskId]?.userAssigned = event.assignedUsername
            updatedAt = createdAt
        }
    }

    @StateTransitionFunc
    fun statusCreatedApply(event: StatusCreatedEvent) {
        val newState = StatusEntity(event.statusId, event.stateName, event.color)
        projectStatuses[newState.id] = newState
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun taskChangedNameApply(event: TaskChangedNameEvent) {
        if (membersUsername.contains(event.issuerUsername)) {
            if (tasks.containsKey(event.taskId)) {
                tasks[event.taskId]?.name = event.newName
                updatedAt = createdAt
            }
        }
    }

    @StateTransitionFunc
    fun statusRemovedApply(event: StatusRemovedEvent) {
        projectStatuses.remove(event.statusId)
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun taskStatusChangedApply(event: TaskChangedStatusEvent) {
        if (membersUsername.contains(event.issuerUsername)) {
            if (tasks.containsKey(event.taskId) && projectStatuses.containsKey(event.statusId)) {
                tasks[event.taskId]?.statusId = event.statusId
                updatedAt = createdAt
            }
        }
    }

}

data class TaskEntity(
    val id: UUID = UUID.randomUUID(),
    var name: String,
    val tagsAssigned: MutableSet<UUID>,
    var userAssigned: String?,
    var statusId: UUID?
)

data class TagEntity(
    val id: UUID = UUID.randomUUID(),
    val name: String
)

data class StatusEntity(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val color: String
)

/**
 * Demonstrates that the transition functions might be representer by "extension" functions, not only class members functions
 */
@StateTransitionFunc
fun ProjectAggregateState.tagAssignedApply(event: TagAssignedToTaskEvent) {
    tasks[event.taskId]?.tagsAssigned?.add(event.tagId)
        ?: throw IllegalArgumentException("No such task: ${event.taskId}")
    updatedAt = createdAt
}
