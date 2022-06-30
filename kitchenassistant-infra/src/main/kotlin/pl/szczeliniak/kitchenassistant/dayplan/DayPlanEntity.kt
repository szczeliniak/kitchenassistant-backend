package pl.szczeliniak.kitchenassistant.dayplan

import org.hibernate.annotations.Where
import java.time.LocalDate
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "day_plans")
@Where(clause = "deleted = false")
data class DayPlanEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_plan_id_generator")
    @SequenceGenerator(name = "day_plan_id_generator", sequenceName = "seq_day_plan_id", allocationSize = 1)
    var id: Int,
    var userId: Int,
    var date: LocalDate,
    @ElementCollection
    @CollectionTable(name = "day_plan_receipt_ids", joinColumns = [JoinColumn(name = "day_plan_id")])
    @Column(name = "receipt_id")
    var receiptIds: MutableSet<Int>,
    var deleted: Boolean,
    var archived: Boolean,
    var createdAt: ZonedDateTime,
    var modifiedAt: ZonedDateTime
)