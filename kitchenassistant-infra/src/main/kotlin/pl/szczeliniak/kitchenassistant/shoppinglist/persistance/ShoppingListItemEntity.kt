package pl.szczeliniak.kitchenassistant.shoppinglist.persistance

import org.hibernate.annotations.Where
import pl.szczeliniak.kitchenassistant.enums.IngredientUnit
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Where(clause = "deleted = false")
data class ShoppingListItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_list_item_id_seq")
    @SequenceGenerator(name = "shopping_list_item_id_seq", sequenceName = "shopping_list_item_id_seq", allocationSize = 1)
    var id: Int,
    var name: String,
    var quantity: String,
    var unit: IngredientUnit?,
    var sequence: Int?,
    var deleted: Boolean,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)