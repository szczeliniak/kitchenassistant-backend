package pl.szczeliniak.kitchenassistant.recipe.db

import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "steps")
data class Step(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "step_id_generator")
    @SequenceGenerator(name = "step_id_generator", sequenceName = "seq_step_id", allocationSize = 1)
    var id: Int = 0,
    var description: String,
    var sequence: Int? = null,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
) {
    constructor() : this(description = "")
}