package pl.szczeliniak.kitchenassistant.shoppinglist.persistance

import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Where(clause = "deleted = false")
data class ShoppingListEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Int,
    var userId: Int,
    var title: String,
    var description: String?,
    @OneToMany(
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    var items: List<ShoppingListItemEntity>,
    var deleted: Boolean,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)