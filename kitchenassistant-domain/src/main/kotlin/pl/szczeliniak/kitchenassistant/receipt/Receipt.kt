package pl.szczeliniak.kitchenassistant.receipt

import pl.szczeliniak.kitchenassistant.shared.exceptions.NotAllowedOperationException
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import java.time.LocalDateTime
import java.util.*

class Receipt(
    private var id_: Int = 0,
    private var userId_: Int,
    private var name_: String,
    private var description_: String?,
    private var author_: String?,
    private var source_: String?,
    private var category_: Category?,
    private var ingredients_: MutableSet<Ingredient> = mutableSetOf(),
    private var steps_: MutableSet<Step> = mutableSetOf(),
    private var photos_: MutableSet<Photo> = mutableSetOf(),
    private var tags_: MutableSet<Tag> = mutableSetOf(),
    private var deleted_: Boolean = false,
    private val createdAt_: LocalDateTime = LocalDateTime.now(),
    private var modifiedAt_: LocalDateTime = LocalDateTime.now()
) {
    val id: Int get() = id_
    val userId: Int get() = userId_
    val name: String get() = name_
    val description: String? get() = description_
    val author: String? get() = author_
    val source: String? get() = source_
    val category: Category? get() = category_
    val ingredients: Set<Ingredient> get() = Collections.unmodifiableSet(ingredients_)
    val steps: Set<Step> get() = Collections.unmodifiableSet(steps_)
    val photos: Set<Photo> get() = Collections.unmodifiableSet(photos_)
    val tags: Set<Tag> get() = Collections.unmodifiableSet(tags_)
    val createdAt: LocalDateTime get() = createdAt_
    val modifiedAt: LocalDateTime get() = modifiedAt_
    val deleted: Boolean get() = deleted_

    fun update(
        name: String,
        description: String? = null,
        category: Category? = null,
        author: String? = null,
        source: String? = null,
        tags: List<Tag> = Collections.emptyList()
    ) {
        this.name_ = name
        this.description_ = description
        this.author_ = author
        this.source_ = source
        this.category_ = category

        this.tags_.clear()
        tags.forEach { new ->
            this.tags_.add(new)
        }

        this.modifiedAt_ = LocalDateTime.now()
    }

    fun markAsDeleted() {
        if (deleted_) {
            throw NotAllowedOperationException("Receipt is already marked as deleted!")
        }
        deleted_ = true
        this.modifiedAt_ = LocalDateTime.now()
    }

    fun addIngredient(ingredient: Ingredient) {
        ingredients_ += ingredient
        this.modifiedAt_ = LocalDateTime.now()
    }

    fun addStep(step: Step) {
        steps_ += step
        this.modifiedAt_ = LocalDateTime.now()
    }

    fun deleteIngredientById(ingredientId: Int): Ingredient {
        val ingredient =
            ingredients.firstOrNull { it.id == ingredientId } ?: throw NotFoundException("Ingredient not found")
        ingredient.markAsDeleted()
        this.modifiedAt_ = LocalDateTime.now()
        return ingredient
    }

    fun deleteStepById(stepId: Int): Step {
        val step = steps.firstOrNull { it.id == stepId } ?: throw NotFoundException("Step not found")
        step.markAsDeleted()
        this.modifiedAt_ = LocalDateTime.now()
        return step
    }

    fun getPhotoById(id: Int): Photo? {
        return photos.firstOrNull { photo -> photo.id == id }
    }

    fun addPhoto(photo: Photo) {
        photos_ += (photo)
        this.modifiedAt_ = LocalDateTime.now()
    }

    fun getStepById(stepId: Int): Step? {
        return steps_.firstOrNull { it.id == stepId }
    }

    fun getTagByName(name: String): Tag? {
        return tags_.firstOrNull { it.name.lowercase() == name.lowercase() }
    }

    fun hasPhotoWithFileId(fileId: Int): Boolean {
        return this.photos_.any { it.fileId == fileId }
    }

}