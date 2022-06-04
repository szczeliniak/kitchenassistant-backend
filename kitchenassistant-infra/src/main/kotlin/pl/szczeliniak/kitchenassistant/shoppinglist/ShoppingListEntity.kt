package pl.szczeliniak.kitchenassistant.shoppinglist

import org.hibernate.annotations.Where
import java.time.LocalDate
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "shopping_lists")
@Where(clause = "deleted = false")
data class ShoppingListEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_list_id_generator")
    @SequenceGenerator(name = "shopping_list_id_generator", sequenceName = "seq_shopping_list_id", allocationSize = 1)
    var id: Int,
    var userId: Int,
    var name: String,
    var description: String?,
    var date: LocalDate?,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_list_id", nullable = false)
    @OrderBy("sequence ASC, id ASC")
    var items: MutableSet<ShoppingListItemEntity>,
    var deleted: Boolean,
    var archived: Boolean,
    var createdAt: ZonedDateTime,
    var modifiedAt: ZonedDateTime
)