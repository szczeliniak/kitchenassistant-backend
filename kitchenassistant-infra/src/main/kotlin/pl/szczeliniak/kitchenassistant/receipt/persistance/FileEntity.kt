package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Where(clause = "deleted = false")
data class FileEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_id_seq")
    @SequenceGenerator(name = "file_id_seq", sequenceName = "file_id_seq", allocationSize = 1)
    var id: Int,
    var name: String,
    var deleted: Boolean,
    var createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime
)