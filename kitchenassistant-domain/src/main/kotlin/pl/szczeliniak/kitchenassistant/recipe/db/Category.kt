package pl.szczeliniak.kitchenassistant.recipe.db

import pl.szczeliniak.kitchenassistant.user.db.User
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "categories")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_generator")
    @SequenceGenerator(name = "category_id_generator", sequenceName = "seq_category_id", allocationSize = 1)
    var id: Int = 0,
    var name: String,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var user: User,
    var sequence: Int? = null,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()
) {
    constructor() : this(name = "", user = User())
}