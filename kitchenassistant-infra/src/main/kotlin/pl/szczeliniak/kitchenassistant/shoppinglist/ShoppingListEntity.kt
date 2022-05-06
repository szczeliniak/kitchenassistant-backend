package pl.szczeliniak.kitchenassistant.shoppinglist

import org.hibernate.annotations.Where
import java.time.LocalDate
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
    var date: LocalDate?,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @OrderBy("sequence ASC, id ASC")
    var items: MutableSet<ShoppingListItemEntity>,
    var deleted: Boolean,
    var archived: Boolean,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)