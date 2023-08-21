package pl.szczeliniak.kitchenassistant.shoppinglist

import org.mapstruct.Mapper
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.dto.response.ShoppingListResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.dto.response.ShoppingListsResponse

@Mapper
abstract class ShoppingListMapper {

    abstract fun map(shoppingList: ShoppingList): ShoppingListsResponse.ShoppingListDto

    abstract fun mapDetails(shoppingList: ShoppingList): ShoppingListResponse.ShoppingListDetailsDto

    abstract fun mapDetails(shoppingListItem: ShoppingListItem): ShoppingListResponse.ShoppingListDetailsDto.ShoppingListItemDto

    abstract fun mapDetails(recipe: Recipe): ShoppingListResponse.ShoppingListDetailsDto.ShoppingListItemDto.SimpleRecipeDto

}