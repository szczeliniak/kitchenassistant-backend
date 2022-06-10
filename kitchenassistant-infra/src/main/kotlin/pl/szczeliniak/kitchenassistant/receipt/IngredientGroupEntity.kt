package pl.szczeliniak.kitchenassistant.receipt

import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "ingredientGroups")
@Where(clause = "deleted = false")
data class IngredientGroupEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_group_id_generator")
    @SequenceGenerator(
        name = "ingredient_group_id_generator",
        sequenceName = "seq_ingredient_group_id",
        allocationSize = 1
    )
    var id: Int,
    var name: String,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_group_id", nullable = false)
    @OrderBy("id ASC")
    var ingredients: MutableSet<IngredientEntity>,
    var deleted: Boolean,
    var createdAt: ZonedDateTime,
    var modifiedAt: ZonedDateTime
)