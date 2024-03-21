package pl.szczeliniak.kitchenassistant.dayplan.db

import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "step_snapshots")
data class StepSnapshot(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "step_snapshot_id_generator")
    @SequenceGenerator(name = "step_snapshot_id_generator", sequenceName = "seq_step_snapshot_id", allocationSize = 1)
    var id: Int = 0,
    var description: String = "",
    var originalStepId: Int? = null,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)