package pl.szczeliniak.kitchenassistant.receipt

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
data class TagEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_id_seq")
    @SequenceGenerator(name = "tag_id_seq", sequenceName = "tag_id_seq", allocationSize = 1)
    var id: Int,
    var name: String,
    var userId: Int,
    var createdAt: ZonedDateTime,
    var modifiedAt: ZonedDateTime
)