package pl.szczeliniak.kitchenassistant.dayplan.db

import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

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
    var name: String = "",
    var quantity: String? = null,
    var originalIngredientId: Int? = null,
    var checked: Boolean = false,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)