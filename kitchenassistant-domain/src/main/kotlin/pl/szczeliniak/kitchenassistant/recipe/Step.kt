package pl.szczeliniak.kitchenassistant.recipe

import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "steps")
@Where(clause = "deleted = false")
data class Step(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "step_id_generator")
    @SequenceGenerator(name = "step_id_generator", sequenceName = "seq_step_id", allocationSize = 1)
    var id: Int = 0,
    var name: String,
    var description: String? = null,
    var sequence: Int? = null,
    var deleted: Boolean = false,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)