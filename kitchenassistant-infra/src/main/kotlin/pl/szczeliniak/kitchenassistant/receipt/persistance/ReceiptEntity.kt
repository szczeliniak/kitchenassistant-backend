package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Where(clause = "deleted = false")
data class ReceiptEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receipt_id_seq")
    @SequenceGenerator(name = "receipt_id_seq", sequenceName = "receipt_id_seq", allocationSize = 1)
    var id: Int,
    var name: String,
    var userId: Int,
    var description: String?,
    var author: String?,
    var source: String?,
    @ManyToOne(fetch = FetchType.LAZY)
    var category: CategoryEntity?,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var ingredients: MutableList<IngredientEntity>,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @OrderBy("sequence")
    var steps: MutableList<StepEntity>,
    @ManyToMany(fetch = FetchType.LAZY)
    var photos: MutableList<FileEntity>,
    @ManyToMany(fetch = FetchType.LAZY)
    var tags: MutableList<TagEntity>,
    var deleted: Boolean,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)