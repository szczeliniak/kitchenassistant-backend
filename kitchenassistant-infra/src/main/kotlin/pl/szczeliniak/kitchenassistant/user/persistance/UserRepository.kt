package pl.szczeliniak.kitchenassistant.user.persistance

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Int>