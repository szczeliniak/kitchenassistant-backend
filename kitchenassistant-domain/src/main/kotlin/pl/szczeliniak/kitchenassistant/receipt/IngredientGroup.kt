package pl.szczeliniak.kitchenassistant.receipt

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import java.time.ZonedDateTime
import java.util.*

data class IngredientGroup(
    private var id_: Int = 0,
    private var name_: String,
    private var ingredients_: MutableSet<Ingredient> = mutableSetOf(),
    private var deleted_: Boolean = false,
    private val createdAt_: ZonedDateTime = ZonedDateTime.now(),
    private var modifiedAt_: ZonedDateTime = ZonedDateTime.now()
) {
    val id: Int get() = id_
    val name: String get() = name_
    val ingredients: Set<Ingredient> get() = Collections.unmodifiableSet(ingredients_)
    val deleted: Boolean get() = deleted_
    val createdAt: ZonedDateTime get() = createdAt_
    val modifiedAt: ZonedDateTime get() = modifiedAt_

    fun markAsDeleted() {
        if (deleted) {
            throw KitchenAssistantException(ErrorCode.INGREDIENT_ALREADY_REMOVED)
        }
        deleted_ = true
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun update(name: String) {
        this.name_ = name
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun addIngredient(ingredient: Ingredient) {
        this.ingredients_ += ingredient
    }

    fun getIngredientById(ingredientId: Int): Ingredient? {
        return this.ingredients_.firstOrNull { it.id == ingredientId }
    }

    fun deleteIngredientById(ingredientId: Int): Ingredient {
        val ingredient = this.ingredients_.firstOrNull { it.id == ingredientId }
            ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_NOT_FOUND)
        ingredient.markAsDeleted()

        return ingredient
    }

}