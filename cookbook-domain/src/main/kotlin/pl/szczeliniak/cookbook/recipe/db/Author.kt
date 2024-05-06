package pl.szczeliniak.cookbook.recipe.db

import pl.szczeliniak.cookbook.user.db.User
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "authors")
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_generator")
    @SequenceGenerator(name = "author_id_generator", sequenceName = "seq_author_id", allocationSize = 1)
    var id: Int = 0,
    var name: String,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var user: User,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
) {
    constructor() : this(name = "", user = User())
}