package pl.szczeliniak.kitchenassistant.receipt

import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "ingredients")
@Where(clause = "deleted = false")
data class IngredientEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_id_generator")
    @SequenceGenerator(name = "ingredient_id_generator", sequenceName = "seq_ingredient_id", allocationSize = 1)
    var id: Int,
    var name: String,
    var quantity: String,
    var deleted: Boolean,
    var createdAt: ZonedDateTime,
    var modifiedAt: ZonedDateTime
)