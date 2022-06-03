package pl.szczeliniak.kitchenassistant.receipt

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
data class AuthorEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    @SequenceGenerator(name = "author_id_seq", sequenceName = "author_id_seq", allocationSize = 1)
    var id: Int,
    var name: String,
    var userId: Int,
    var createdAt: ZonedDateTime,
    var modifiedAt: ZonedDateTime
)