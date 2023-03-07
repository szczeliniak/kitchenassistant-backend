package pl.szczeliniak.kitchenassistant.recipe.db

import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "categories")
@Where(clause = "deleted = false")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_generator")
    @SequenceGenerator(name = "category_id_generator", sequenceName = "seq_category_id", allocationSize = 1)
    var id: Int = 0,
    var name: String,
    var userId: Int,
    var sequence: Int? = null,
    var deleted: Boolean = false,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)