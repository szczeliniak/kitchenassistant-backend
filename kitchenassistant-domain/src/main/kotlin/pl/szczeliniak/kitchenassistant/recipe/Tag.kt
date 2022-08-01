package pl.szczeliniak.kitchenassistant.recipe

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "tags")
data class Tag(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_id_generator")
    @SequenceGenerator(name = "tag_id_generator", sequenceName = "seq_tag_id", allocationSize = 1)
    var id: Int = 0,
    var name: String,
    var userId: Int,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)