package pl.szczeliniak.kitchenassistant.receipt.persistance

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class ReceiptEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Int,
    var name: String,
    var userId: Int,
    var description: String?,
    var author: String?,
    var source: String?,
    @OneToMany(
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    var ingredients: List<IngredientEntity>,
    @OneToMany(
        cascade = [CascadeType.ALL], fetch = FetchType.LAZY
    )
    var steps: List<StepEntity>,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)