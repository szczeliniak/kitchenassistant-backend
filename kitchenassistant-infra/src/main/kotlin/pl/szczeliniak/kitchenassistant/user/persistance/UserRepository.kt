package pl.szczeliniak.kitchenassistant.user.persistance

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository : JpaRepository<UserEntity, Int> {

    @Query("SELECT u FROM UserEntity u WHERE u.email = ?1")
    fun findByEmail(email: String): Optional<UserEntity>

}