package pl.szczeliniak.kitchenassistant.dayplan.db

import java.time.LocalDate
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "day_plans")
data class DayPlan(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_plan_id_generator")
    @SequenceGenerator(name = "day_plan_id_generator", sequenceName = "seq_day_plan_id", allocationSize = 1)
    var id: Int = 0,
    var userId: Int,
    var date: LocalDate,
    @ManyToMany
    @JoinTable(
        name = "day_plan_recipe_snapshot_ids",
        joinColumns = [JoinColumn(name = "day_plan_id")],
        inverseJoinColumns = [JoinColumn(name = "recipe_snapshot_id")]
    )
    var recipes: MutableSet<RecipeSnapshot> = mutableSetOf(),
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
) {
    constructor() : this(userId = 0, date = LocalDate.now())
}