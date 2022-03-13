package pl.szczeliniak.kitchenassistant.receipt.persistance

import pl.szczeliniak.kitchenassistant.receipt.IngredientUnit
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class IngredientEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Int,
    var name: String,
    var quantity: String,
    var unit: IngredientUnit?,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)