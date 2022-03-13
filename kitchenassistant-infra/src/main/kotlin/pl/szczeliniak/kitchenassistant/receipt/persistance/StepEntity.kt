package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@Where(clause = "deleted = false")
data class StepEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Int,
    var title: String,
    var description: String?,
    var sequence: Int?,
    var deleted: Boolean,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)