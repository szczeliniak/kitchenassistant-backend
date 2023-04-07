package pl.szczeliniak.kitchenassistant.recipe.db

import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "recipes")
@Where(clause = "deleted = false")
class Recipe(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_id_generator")
        @SequenceGenerator(name = "recipe_id_generator", sequenceName = "seq_recipe_id", allocationSize = 1)
        var id: Int = 0,
        var name: String,
        var userId: Int,
        var description: String? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "author_id")
        var author: Author? = null,
        var source: String? = null,
        var favorite: Boolean = false,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        var category: Category? = null,
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name = "recipe_id", nullable = false)
        @OrderBy("id ASC")
        var ingredientGroups: MutableSet<IngredientGroup> = mutableSetOf(),
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name = "recipe_id", nullable = false)
        @OrderBy("sequence ASC, id ASC")
        var steps: MutableSet<Step> = mutableSetOf(),
        @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinTable(
                name = "recipes_photos",
                joinColumns = [JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false)],
                inverseJoinColumns = [JoinColumn(name = "photo_id", referencedColumnName = "id", nullable = false)]
        )
        var photo: Photo?,
        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinTable(
                name = "recipe_tags",
                joinColumns = [JoinColumn(name = "recipe_id", nullable = false)],
                inverseJoinColumns = [JoinColumn(name = "tag_id", nullable = false)]
        )
        var tags: MutableSet<Tag> = mutableSetOf(),
        var deleted: Boolean = false,
        var createdAt: ZonedDateTime = ZonedDateTime.now(),
        var modifiedAt: ZonedDateTime = ZonedDateTime.now()
)