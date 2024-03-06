package pl.szczeliniak.kitchenassistant.dayplan.db

import java.time.LocalDate
import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "day_plans")
data class DayPlan(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_plan_id_generator")
    @SequenceGenerator(name = "day_plan_id_generator", sequenceName = "seq_day_plan_id", allocationSize = 1)
    var id: Int = 0,
    var userId: Int = 0,
    var date: LocalDate = LocalDate.now(),
    @ManyToMany
    @JoinTable(
        name = "day_plan_recipe_snapshot_ids",
        joinColumns = [JoinColumn(name = "day_plan_id")],
        inverseJoinColumns = [JoinColumn(name = "recipe_snapshot_id")]
    )
    var recipes: MutableList<RecipeSnapshot> = mutableListOf(),
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)