package pl.szczeliniak.kitchenassistant.receipt

import pl.szczeliniak.kitchenassistant.exceptions.NotAllowedOperationException
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import java.time.LocalDateTime

class Receipt(
    private var id_: Int = 0,
    private var userId_: Int,
    private var name_: String,
    private var description_: String?,
    private var author_: String?,
    private var source_: String?,
    private var ingredients_: MutableList<Ingredient> = mutableListOf(),
    private var steps_: MutableList<Step> = mutableListOf(),
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
    val ingredients: MutableList<Ingredient> get() = ingredients_
    val steps: MutableList<Step> get() = steps_
    val createdAt: LocalDateTime get() = createdAt_
    val modifiedAt: LocalDateTime get() = modifiedAt_
    val deleted: Boolean get() = deleted_

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

    fun deleteIngredientById(ingredientId: Int) {
        val ingredient =
            ingredients.firstOrNull { it.id == ingredientId } ?: throw NotFoundException("Ingredient not found")
        ingredient.markAsDeleted()
        this.modifiedAt_ = LocalDateTime.now()
    }

    fun deleteStepById(stepId: Int) {
        val step = steps.firstOrNull { it.id == stepId } ?: throw NotFoundException("Step not found")
        step.markAsDeleted()
        this.modifiedAt_ = LocalDateTime.now()
    }

}