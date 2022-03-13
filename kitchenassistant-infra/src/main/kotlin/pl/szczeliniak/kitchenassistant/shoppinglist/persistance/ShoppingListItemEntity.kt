package pl.szczeliniak.kitchenassistant.shoppinglist.persistance

import org.hibernate.annotations.Where
import pl.szczeliniak.kitchenassistant.enums.IngredientUnit
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@Where(clause = "deleted = false")
data class ShoppingListItemEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Int,
    var name: String,
    var quantity: String,
    var unit: IngredientUnit?,
    var sequence: Int?,
    var deleted: Boolean,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)