package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.hibernate.annotations.Where
import pl.szczeliniak.kitchenassistant.receipt.IngredientUnit
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@Where(clause = "deleted = false")
data class IngredientEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Int,
    var name: String,
    var quantity: String,
    var unit: IngredientUnit?,
    var deleted: Boolean,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)