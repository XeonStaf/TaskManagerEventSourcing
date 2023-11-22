package ru.quipy.projections.data.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.quipy.projections.data.model.ProjectEntity
import java.util.*

@Repository
interface ProjectRepository : JpaRepository<ProjectEntity, UUID>