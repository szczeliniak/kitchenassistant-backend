package pl.szczeliniak.kitchenassistant.receipt

import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "photos")
@Where(clause = "deleted = false")
data class PhotoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_id_generator")
    @SequenceGenerator(name = "photo_id_generator", sequenceName = "seq_photo_id", allocationSize = 1)
    var id: Int,
    var name: String,
    var userId: Int,
    var deleted: Boolean,
    var createdAt: ZonedDateTime,
    var modifiedAt: ZonedDateTime
)