package pl.szczeliniak.kitchenassistant.dayplan.db

import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.recipe.db.Category
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "recipe_snapshots")
class RecipeSnapshot(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_snapshot_id_generator")
    @SequenceGenerator(
        name = "recipe_snapshot_id_generator",
        sequenceName = "seq_recipe_snapshot_id",
        allocationSize = 1
    )
    var id: Int = 0,
    var name: String,
    var description: String? = null,
    var originalRecipeId: Int? = null,
    var source: String? = null,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_snapshot_id", nullable = false)
    @OrderBy("id ASC")
    var ingredientGroups: MutableSet<IngredientGroupSnapshot> = mutableSetOf(),
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_snapshot_id", nullable = false)
    @OrderBy("sequence ASC, id ASC")
    var steps: MutableSet<StepSnapshot> = mutableSetOf(),
    var photoName: String?,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
) {
    constructor() : this(name = "", photoName = null)
}