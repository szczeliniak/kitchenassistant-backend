package pl.szczeliniak.kitchenassistant.receipt

import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "receipts")
@Where(clause = "deleted = false")
data class ReceiptEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receipt_id_generator")
    @SequenceGenerator(name = "receipt_id_generator", sequenceName = "seq_receipt_id", allocationSize = 1)
    var id: Int,
    var name: String,
    var userId: Int,
    var description: String?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    var author: AuthorEntity?,
    var source: String?,
    var favorite: Boolean,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: CategoryEntity?,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    @OrderBy("id ASC")
    var ingredientGroups: MutableSet<IngredientGroupEntity>,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    @OrderBy("sequence ASC, id ASC")
    var steps: MutableSet<StepEntity>,
    @OneToMany(cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    @OrderBy("id")
    var photos: MutableSet<PhotoEntity>,
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "receipt_tags",
        joinColumns = [JoinColumn(name = "receipt_id", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "tag_id", nullable = false)]
    )
    var tags: MutableSet<TagEntity>,
    var deleted: Boolean,
    var createdAt: ZonedDateTime,
    var modifiedAt: ZonedDateTime
)