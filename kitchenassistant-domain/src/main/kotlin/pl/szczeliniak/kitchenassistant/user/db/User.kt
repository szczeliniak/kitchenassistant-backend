package pl.szczeliniak.kitchenassistant.user.db

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", sequenceName = "seq_user_id", allocationSize = 1)
    var id: Int = 0,
    var email: String,
    var password: String? = null,
    var name: String,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)