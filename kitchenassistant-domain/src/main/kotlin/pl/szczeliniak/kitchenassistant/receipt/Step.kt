package pl.szczeliniak.kitchenassistant.receipt

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
    var id: Int,
    var name: String,
    var description: String?,
    var sequence: Int?,
    var deleted: Boolean = false,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)