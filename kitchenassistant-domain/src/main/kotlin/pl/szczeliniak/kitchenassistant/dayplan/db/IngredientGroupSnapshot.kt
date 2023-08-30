package pl.szczeliniak.kitchenassistant.dayplan.db

import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroup
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "ingredient_group_snapshots")
data class IngredientGroupSnapshot(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_group_snapshot_id_generator")
    @SequenceGenerator(
        name = "ingredient_group_snapshot_id_generator",
        sequenceName = "seq_ingredient_group_snapshot_id",
        allocationSize = 1
    )
    var id: Int = 0,
    var name: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_group_id")
    var ingredientGroup: IngredientGroup,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_group_snapshot_id", nullable = false)
    @OrderBy("id ASC")
    var ingredients: MutableSet<IngredientSnapshot> = mutableSetOf(),
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
) {
    constructor() : this(name = "", ingredientGroup = IngredientGroup())
}