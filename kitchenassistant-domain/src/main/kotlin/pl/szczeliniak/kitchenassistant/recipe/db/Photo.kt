package pl.szczeliniak.kitchenassistant.recipe.db

import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "photos")
@Where(clause = "deleted = false")
data class Photo(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_id_generator")
    @SequenceGenerator(name = "photo_id_generator", sequenceName = "seq_photo_id", allocationSize = 1)
    var id: Int = 0,
    var name: String,
    var userId: Int,
    var deleted: Boolean = false,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)