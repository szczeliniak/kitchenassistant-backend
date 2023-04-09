package pl.szczeliniak.kitchenassistant.recipe.db

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "steps")
data class Step(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "step_id_generator")
    @SequenceGenerator(name = "step_id_generator", sequenceName = "seq_step_id", allocationSize = 1)
    var id: Int = 0,
    var name: String,
    var description: String? = null,
    var sequence: Int? = null,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)