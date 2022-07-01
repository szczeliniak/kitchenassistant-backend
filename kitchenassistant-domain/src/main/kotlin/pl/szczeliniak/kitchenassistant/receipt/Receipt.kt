package pl.szczeliniak.kitchenassistant.receipt

import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "receipts")
@Where(clause = "deleted = false")
class Receipt(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receipt_id_generator")
    @SequenceGenerator(name = "receipt_id_generator", sequenceName = "seq_receipt_id", allocationSize = 1)
    var id: Int = 0,
    var name: String,
    var userId: Int,
    var description: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    var author: Author? = null,
    var source: String? = null,
    var favorite: Boolean = false,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: Category? = null,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    @OrderBy("id ASC")
    var ingredientGroups: MutableSet<IngredientGroup> = mutableSetOf(),
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    @OrderBy("sequence ASC, id ASC")
    var steps: MutableSet<Step> = mutableSetOf(),
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    @OrderBy("id")
    var photos: MutableSet<Photo> = mutableSetOf(),
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "receipt_tags",
        joinColumns = [JoinColumn(name = "receipt_id", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "tag_id", nullable = false)]
    )
    var tags: MutableSet<Tag> = mutableSetOf(),
    var deleted: Boolean = false,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)