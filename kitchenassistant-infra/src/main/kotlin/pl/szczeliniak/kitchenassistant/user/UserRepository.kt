package pl.szczeliniak.kitchenassistant.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Int>