package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Where(clause = "deleted = false")
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
    var ingredients: MutableList<IngredientEntity>,
    @OneToMany(
        cascade = [CascadeType.ALL], fetch = FetchType.LAZY
    )
    var steps: MutableList<StepEntity>,
    var deleted: Boolean,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)