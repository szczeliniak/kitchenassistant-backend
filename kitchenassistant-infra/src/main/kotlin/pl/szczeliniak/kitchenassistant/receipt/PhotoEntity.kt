package pl.szczeliniak.kitchenassistant.receipt

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "photos")
data class PhotoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_id_generator")
    @SequenceGenerator(name = "photo_id_generator", sequenceName = "seq_photo_id", allocationSize = 1)
    var id: Int,
    var fileId: Int,
    var createdAt: ZonedDateTime,
    var modifiedAt: ZonedDateTime
)