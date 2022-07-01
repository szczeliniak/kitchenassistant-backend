package pl.szczeliniak.kitchenassistant.receipt

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "authors")
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_generator")
    @SequenceGenerator(name = "author_id_generator", sequenceName = "seq_author_id", allocationSize = 1)
    var id: Int,
    var name: String,
    var userId: Int,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)