package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Where(clause = "deleted = false")
data class StepEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "step_id_seq")
    @SequenceGenerator(name = "step_id_seq", sequenceName = "step_id_seq", allocationSize = 1)
    var id: Int,
    var title: String,
    var description: String?,
    var sequence: Int?,
    var deleted: Boolean,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)