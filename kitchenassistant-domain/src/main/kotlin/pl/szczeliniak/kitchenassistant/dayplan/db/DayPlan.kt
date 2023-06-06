package pl.szczeliniak.kitchenassistant.dayplan.db

import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.user.db.User
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var user: User,
    var date: LocalDate,
    @ManyToMany
    @JoinTable(
        name = "day_plan_recipe_ids",
        joinColumns = [JoinColumn(name = "day_plan_id")],
        inverseJoinColumns = [JoinColumn(name = "recipe_id")]
    )
    var recipes: MutableSet<Recipe> = mutableSetOf(),
    var automaticArchiving: Boolean = false,
    var archived: Boolean = false,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)