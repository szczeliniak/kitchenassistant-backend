package pl.szczeliniak.kitchenassistant.shoppinglist

import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "shopping_list_items")
@Where(clause = "deleted = false")
data class ShoppingListItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_list_item_id_generator")
    @SequenceGenerator(name = "shopping_list_item_id_generator", sequenceName = "seq_shopping_list_item_id", allocationSize = 1)
    var id: Int,
    var name: String,
    var quantity: String?,
    var sequence: Int?,
    var receiptId: Int?,
    var deleted: Boolean,
    var completed: Boolean,
    var createdAt: ZonedDateTime,
    var modifiedAt: ZonedDateTime
)