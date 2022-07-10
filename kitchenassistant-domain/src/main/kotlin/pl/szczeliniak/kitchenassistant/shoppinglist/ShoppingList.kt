package pl.szczeliniak.kitchenassistant.shoppinglist

import org.hibernate.annotations.Where
import java.time.LocalDate
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "shopping_lists")
@Where(clause = "deleted = false")
data class ShoppingList(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_list_id_generator")
    @SequenceGenerator(name = "shopping_list_id_generator", sequenceName = "seq_shopping_list_id", allocationSize = 1)
    var id: Int = 0,
    var userId: Int,
    var name: String,
    var description: String? = null,
    var date: LocalDate? = null,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_list_id", nullable = false)
    @OrderBy("sequence ASC, id ASC")
    var items: MutableSet<ShoppingListItem> = mutableSetOf(),
    var automaticArchiving: Boolean = false,
    var deleted: Boolean = false,
    var archived: Boolean = false,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)