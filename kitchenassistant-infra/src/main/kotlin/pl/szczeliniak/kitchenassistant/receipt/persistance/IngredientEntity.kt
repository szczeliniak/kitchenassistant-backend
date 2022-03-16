package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.hibernate.annotations.Where
import pl.szczeliniak.kitchenassistant.enums.IngredientUnit
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Where(clause = "deleted = false")
data class IngredientEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_id_seq")
    @SequenceGenerator(name = "ingredient_id_seq", sequenceName = "ingredient_id_seq", allocationSize = 1)
    var id: Int,
    var name: String,
    var quantity: String,
    var unit: IngredientUnit?,
    var deleted: Boolean,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)