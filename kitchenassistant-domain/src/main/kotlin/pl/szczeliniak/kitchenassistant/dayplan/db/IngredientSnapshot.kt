package pl.szczeliniak.kitchenassistant.dayplan.db

import pl.szczeliniak.kitchenassistant.recipe.db.Ingredient
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "ingredient_snapshots")
data class IngredientSnapshot(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_snapshot_id_generator")
    @SequenceGenerator(
        name = "ingredient_snapshot_id_generator",
        sequenceName = "seq_ingredient_snapshot_id",
        allocationSize = 1
    )
    var id: Int = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    var ingredient: Ingredient,
    var name: String,
    var quantity: String? = null,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
) {
    constructor() : this(name = "", ingredient = Ingredient())
}