package pl.szczeliniak.kitchenassistant.shoppinglist.db

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "shopping_list_items")
data class ShoppingListItem(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_list_item_id_generator")
    @SequenceGenerator(
        name = "shopping_list_item_id_generator",
        sequenceName = "seq_shopping_list_item_id",
        allocationSize = 1
    )
    var id: Int = 0,
    var name: String,
    var quantity: String? = null,
    var sequence: Int? = null,
    var recipeId: Int? = null,
    var completed: Boolean = false,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)