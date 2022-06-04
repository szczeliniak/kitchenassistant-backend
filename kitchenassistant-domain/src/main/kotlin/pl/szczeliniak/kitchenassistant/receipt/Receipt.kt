package pl.szczeliniak.kitchenassistant.receipt

import pl.szczeliniak.kitchenassistant.shared.exceptions.NotAllowedOperationException
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import java.time.ZonedDateTime
import java.util.*

class Receipt(
    private var id_: Int = 0,
    private var userId_: Int,
    private var name_: String,
    private var description_: String?,
    private var author_: Author?,
    private var source_: String?,
    private var favorite_: Boolean = false,
    private var category_: Category?,
    private var ingredients_: MutableSet<Ingredient> = mutableSetOf(),
    private var steps_: MutableSet<Step> = mutableSetOf(),
    private var photos_: MutableSet<Photo> = mutableSetOf(),
    private var tags_: MutableSet<Tag> = mutableSetOf(),
    private var deleted_: Boolean = false,
    private val createdAt_: ZonedDateTime = ZonedDateTime.now(),
    private var modifiedAt_: ZonedDateTime = ZonedDateTime.now()
) {
    val id: Int get() = id_
    val userId: Int get() = userId_
    val name: String get() = name_
    val description: String? get() = description_
    val author: Author? get() = author_
    val source: String? get() = source_
    val category: Category? get() = category_
    val ingredients: Set<Ingredient> get() = Collections.unmodifiableSet(ingredients_)
    val steps: Set<Step> get() = Collections.unmodifiableSet(steps_)
    val photos: Set<Photo> get() = Collections.unmodifiableSet(photos_)
    val tags: Set<Tag> get() = Collections.unmodifiableSet(tags_)
    val favorite: Boolean get() = favorite_
    val createdAt: ZonedDateTime get() = createdAt_
    val modifiedAt: ZonedDateTime get() = modifiedAt_
    val deleted: Boolean get() = deleted_

    fun update(
        name: String,
        description: String? = null,
        category: Category? = null,
        author: Author? = null,
        source: String? = null,
        tags: List<Tag> = Collections.emptyList(),
        photos: List<Photo> = Collections.emptyList()
    ) {
        this.name_ = name
        this.description_ = description
        this.author_ = author
        this.source_ = source
        this.category_ = category

        updateTags(tags)
        updatePhotos(photos)

        this.modifiedAt_ = ZonedDateTime.now()
    }

    private fun updateTags(tags: List<Tag>) {
        this.tags_.clear()
        tags.forEach { new ->
            this.tags_.add(new)
        }
    }

    private fun updatePhotos(photos: List<Photo>) {
        this.photos_.clear()
        photos.forEach { new ->
            this.photos_.add(new)
        }
    }

    fun markAsDeleted() {
        if (deleted_) {
            throw NotAllowedOperationException("Receipt is already marked as deleted!")
        }
        deleted_ = true
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun markAsFavorite(isFavorite: Boolean) {
        favorite_ = isFavorite
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun addIngredient(ingredient: Ingredient) {
        ingredients_ += ingredient
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun addStep(step: Step) {
        steps_ += step
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun deleteIngredientById(ingredientId: Int): Ingredient {
        val ingredient =
            ingredients.firstOrNull { it.id == ingredientId } ?: throw NotFoundException("Ingredient not found")
        ingredient.markAsDeleted()
        this.modifiedAt_ = ZonedDateTime.now()
        return ingredient
    }

    fun deleteStepById(stepId: Int): Step {
        val step = steps.firstOrNull { it.id == stepId } ?: throw NotFoundException("Step not found")
        step.markAsDeleted()
        this.modifiedAt_ = ZonedDateTime.now()
        return step
    }

    fun deletePhotoById(id: Int): Photo {
        val photo = photos_.firstOrNull { it.id == id } ?: throw NotFoundException("Photo not found")
        photos_.remove(photo)
        return photo
    }

    fun getPhotoById(photoId: Int): Photo? {
        return photos.firstOrNull { photo -> photo.id == photoId }
    }

    fun addPhoto(photo: Photo) {
        photos_ += (photo)
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun getStepById(stepId: Int): Step {
        return steps_.firstOrNull { it.id == stepId } ?: throw NotFoundException("Step not found")
    }

    fun getTagByName(name: String): Tag? {
        return tags_.firstOrNull { it.name.lowercase() == name.lowercase() }
    }

    fun hasPhotoWithId(photoId: Int): Boolean {
        return this.photos_.any { it.id == photoId }
    }

}