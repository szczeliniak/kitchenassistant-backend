package pl.szczeliniak.kitchenassistant.receipt

import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "categories")
@Where(clause = "deleted = false")
data class CategoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_generator")
    @SequenceGenerator(name = "category_id_generator", sequenceName = "seq_category_id", allocationSize = 1)
    var id: Int,
    var name: String,
    var userId: Int,
    var deleted: Boolean,
    var createdAt: ZonedDateTime,
    var modifiedAt: ZonedDateTime
)