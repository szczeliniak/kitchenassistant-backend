package pl.szczeliniak.kitchenassistant.user

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", sequenceName = "seq_user_id", allocationSize = 1)
    var id: Int,
    var email: String,
    var password: String?,
    var name: String,
    var createdAt: ZonedDateTime,
    var modifiedAt: ZonedDateTime
)