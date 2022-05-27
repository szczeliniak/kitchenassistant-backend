package pl.szczeliniak.kitchenassistant.receipt

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
data class PhotoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_id_seq")
    @SequenceGenerator(name = "photo_id_seq", sequenceName = "photo_id_seq", allocationSize = 1)
    var id: Int,
    var fileId: Int,
    var createdAt: ZonedDateTime,
    var modifiedAt: ZonedDateTime
)