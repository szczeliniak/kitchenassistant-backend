package pl.szczeliniak.kitchenassistant.dayplan.db

import java.time.ZonedDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OrderBy
import javax.persistence.SequenceGenerator
import javax.persistence.Table

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
    var name: String = "",
    var description: String? = null,
    var originalRecipeId: Int = 0,
    var source: String? = null,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_snapshot_id", nullable = false)
    @OrderBy("id ASC")
    var ingredientGroups: MutableList<IngredientGroupSnapshot> = mutableListOf(),
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_snapshot_id", nullable = false)
    @OrderBy("id ASC")
    var stepGroups: MutableList<StepGroupSnapshot> = mutableListOf(),
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)