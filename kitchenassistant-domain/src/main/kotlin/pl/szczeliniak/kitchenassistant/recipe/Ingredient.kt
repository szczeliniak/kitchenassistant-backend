package pl.szczeliniak.kitchenassistant.recipe

import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "ingredients")
@Where(clause = "deleted = false")
data class Ingredient(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_id_generator")
    @SequenceGenerator(name = "ingredient_id_generator", sequenceName = "seq_ingredient_id", allocationSize = 1)
    var id: Int = 0,
    var name: String,
    var quantity: String? = null,
    var deleted: Boolean = false,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)