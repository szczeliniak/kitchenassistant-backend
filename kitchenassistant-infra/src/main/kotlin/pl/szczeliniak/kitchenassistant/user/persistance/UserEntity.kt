package pl.szczeliniak.kitchenassistant.user.persistance

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Int,
    var email: String,
    var password: String,
    var name: String,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)