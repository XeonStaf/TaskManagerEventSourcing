package ru.quipy.projections

import org.springframework.stereotype.Service
import ru.quipy.api.ProjectAggregate
import ru.quipy.api.ProjectCreatedEvent
import ru.quipy.projections.data.model.ProjectEntity
import ru.quipy.projections.data.repository.ProjectRepository
import ru.quipy.projections.data.repository.UserRepository
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
    aggregateClass = ProjectAggregate::class, subscriberName = "demo-subs-stream"
)
class ProjectProjection(val projectRepository: ProjectRepository,
                        val userRepository: UserRepository) {

    @SubscribeEvent
    fun projectCreatedSubscriber(event: ProjectCreatedEvent) {
        val creator = userRepository.findById(event.creatorId)
            .orElseThrow()
        val projectEntity = ProjectEntity()
        projectEntity.id = event.projectId
        projectEntity.title = event.title
        projectEntity.creator = creator
        projectEntity.createdAt = event.createdAt
        projectEntity.updatedAt = event.createdAt
        projectRepository.save(projectEntity)
    }

}