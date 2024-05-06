package pl.szczeliniak.cookbook.dayplan.db

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
    var originalIngredientGroupId: Int? = null,
    var name: String? = null,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_group_snapshot_id", nullable = false)
    @OrderBy("id ASC")
    var ingredients: MutableList<IngredientSnapshot> = mutableListOf(),
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)