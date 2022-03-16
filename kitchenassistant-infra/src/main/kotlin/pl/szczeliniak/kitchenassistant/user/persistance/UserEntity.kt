package pl.szczeliniak.kitchenassistant.user.persistance

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    var id: Int,
    var email: String,
    var password: String,
    var name: String,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)