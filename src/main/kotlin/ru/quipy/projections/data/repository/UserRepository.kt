package ru.quipy.projections.data.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.quipy.projections.data.model.UserEntity
interface UserRepository : JpaRepository<UserEntity, String>