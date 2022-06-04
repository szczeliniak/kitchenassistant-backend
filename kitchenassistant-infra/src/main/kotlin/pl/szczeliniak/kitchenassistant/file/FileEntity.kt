package pl.szczeliniak.kitchenassistant.file

import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "files")
@Where(clause = "deleted = false")
data class FileEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_id_generator")
    @SequenceGenerator(name = "file_id_generator", sequenceName = "seq_file_id", allocationSize = 1)
    var id: Int,
    var name: String,
    var userId: Int,
    var deleted: Boolean,
    var createdAt: ZonedDateTime,
    var modifiedAt: ZonedDateTime
)