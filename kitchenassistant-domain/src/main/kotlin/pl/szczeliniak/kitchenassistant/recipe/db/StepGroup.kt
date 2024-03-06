package pl.szczeliniak.kitchenassistant.recipe.db

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
@Table(name = "step_groups")
data class StepGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "step_group_id_generator")
    @SequenceGenerator(
        name = "step_group_id_generator",
        sequenceName = "seq_step_group_id",
        allocationSize = 1
    )
    var id: Int = 0,
    var name: String? = null,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "step_group_id", nullable = false)
    @OrderBy("sequence ASC, id ASC")
    var steps: MutableSet<Step> = mutableSetOf(),
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)