package pl.szczeliniak.kitchenassistant.dayplan.db

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "step_snapshots")
data class StepSnapshot(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "step_snapshot_id_generator")
    @SequenceGenerator(name = "step_snapshot_id_generator", sequenceName = "seq_step_snapshot_id", allocationSize = 1)
    var id: Int = 0,
    var description: String,
    var originalStepId: Int? = null,
    var photoName: String? = null,
    var sequence: Int? = null,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
) {
    constructor() : this(description = "")
}