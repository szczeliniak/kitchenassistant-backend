package pl.szczeliniak.kitchenassistant.recipe.db

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "ingredient_groups")
data class IngredientGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_group_id_generator")
    @SequenceGenerator(
        name = "ingredient_group_id_generator",
        sequenceName = "seq_ingredient_group_id",
        allocationSize = 1
    )
    var id: Int = 0,
    var name: String? = null,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_group_id", nullable = false)
    @OrderBy("id ASC")
    var ingredients: MutableSet<Ingredient> = mutableSetOf(),
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)