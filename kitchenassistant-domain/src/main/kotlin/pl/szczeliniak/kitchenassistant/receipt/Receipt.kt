package pl.szczeliniak.kitchenassistant.receipt

import pl.szczeliniak.kitchenassistant.exceptions.NotAllowedOperationException
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
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
    private var ingredients_: MutableList<Ingredient> = mutableListOf(),
    private var steps_: MutableList<Step> = mutableListOf(),
    private var photos_: MutableList<Photo> = mutableListOf(),
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
    val ingredients: List<Ingredient> get() = Collections.unmodifiableList(ingredients_)
    val steps: List<Step> get() = Collections.unmodifiableList(steps_)
    val photos: List<Photo> get() = Collections.unmodifiableList(photos_)
    val createdAt: LocalDateTime get() = createdAt_
    val modifiedAt: LocalDateTime get() = modifiedAt_
    val deleted: Boolean get() = deleted_

    fun update(
        name: String,
        description: String? = null,
        category: Category? = null,
        author: String? = null,
        source: String? = null
    ) {
        this.name_ = name
        this.description_ = description
        this.author_ = author
        this.source_ = source
        this.category_ = category
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

    fun deletePhotoByName(name: String): Photo {
        val photo = photos.firstOrNull { it.name == name } ?: throw NotFoundException("Photo not found")
        photo.markAsDeleted()
        this.modifiedAt_ = LocalDateTime.now()
        return photo
    }

    fun getPhotoByName(name: String): Photo? {
        return photos.firstOrNull { photo -> photo.name == name }
    }

    fun addPhoto(photo: Photo) {
        photos_ += (photo)
        this.modifiedAt_ = LocalDateTime.now()
    }

    fun getStepById(stepId: Int): Step? {
        return steps_.firstOrNull { it.id == stepId }
    }

}