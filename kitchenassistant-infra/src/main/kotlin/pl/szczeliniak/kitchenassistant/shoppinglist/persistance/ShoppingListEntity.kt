package pl.szczeliniak.kitchenassistant.shoppinglist.persistance

import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Where(clause = "deleted = false")
data class ShoppingListEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_list_id_seq")
    @SequenceGenerator(name = "shopping_list_id_seq", sequenceName = "shopping_list_id_seq", allocationSize = 1)
    var id: Int,
    var userId: Int,
    var name: String,
    var description: String?,
    @OneToMany(
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    var items: MutableList<ShoppingListItemEntity>,
    var deleted: Boolean,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)