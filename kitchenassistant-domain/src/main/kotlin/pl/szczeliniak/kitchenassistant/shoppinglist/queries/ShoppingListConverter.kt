package pl.szczeliniak.kitchenassistant.shoppinglist.queries

import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListDetailsDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.SimpleRecipeDto

open class ShoppingListConverter {

    open fun map(shoppingList: ShoppingList): ShoppingListDto {
        return ShoppingListDto(
            shoppingList.id,
            shoppingList.name,
            shoppingList.description,
            shoppingList.date
        )
    }

    open fun mapDetails(shoppingList: ShoppingList): ShoppingListDetailsDto {
        return ShoppingListDetailsDto(
            shoppingList.id,
            shoppingList.name,
            shoppingList.description,
            shoppingList.date,
            shoppingList.items.map { map(it) }.toSet(),
            shoppingList.automaticArchiving
        )
    }

    open fun map(shoppingListItem: ShoppingListItem): ShoppingListItemDto {
        return ShoppingListItemDto(
            shoppingListItem.id,
            shoppingListItem.name,
            shoppingListItem.quantity,
            shoppingListItem.sequence,
            shoppingListItem.completed,
            shoppingListItem.recipe?.let { map(it) }
        )
    }

    fun map(recipe: Recipe): SimpleRecipeDto {
        return SimpleRecipeDto(recipe.id, recipe.name)
    }

}