package pl.szczeliniak.kitchenassistant.shoppinglist.db

import pl.szczeliniak.kitchenassistant.user.db.User
import java.time.LocalDate
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "shopping_lists")
data class ShoppingList(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_list_id_generator")
    @SequenceGenerator(name = "shopping_list_id_generator", sequenceName = "seq_shopping_list_id", allocationSize = 1)
    var id: Int = 0,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var user: User,
    var name: String,
    var description: String? = null,
    var date: LocalDate? = null,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_list_id", nullable = false)
    @OrderBy("sequence ASC, id ASC")
    var items: MutableSet<ShoppingListItem> = mutableSetOf(),
    var archived: Boolean = false,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)