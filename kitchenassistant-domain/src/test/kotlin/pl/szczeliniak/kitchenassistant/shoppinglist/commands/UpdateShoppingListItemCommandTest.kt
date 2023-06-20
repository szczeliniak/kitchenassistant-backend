package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListItemRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItemDao
import pl.szczeliniak.kitchenassistant.user.db.User

internal class UpdateShoppingListItemCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @Mock
    private lateinit var shoppingListItemDao: ShoppingListItemDao

    @Mock
    private lateinit var recipeDao: RecipeDao

    @InjectMocks
    private lateinit var updateShoppingListItemCommand: UpdateShoppingListItemCommand

    @Test
    fun shouldUpdateShoppingList() {
        val recipe = recipe()
        val shoppingListItem = shoppingListItem(recipe)
        val shoppingList = shoppingList(mutableSetOf(shoppingListItem))

        whenever(shoppingListDao.findById(1)).thenReturn(shoppingList)
        whenever(recipeDao.findById(3)).thenReturn(recipe)
        whenever(shoppingListItemDao.save(shoppingListItem)).thenReturn(shoppingListItem)

        val result = updateShoppingListItemCommand.execute(1, 2, updateShoppingListItemRequest())

        assertThat(result).isEqualTo(SuccessResponse(2))
        assertThat(shoppingListItem.name).isEqualTo("NAME")
        assertThat(shoppingListItem.quantity).isEqualTo("QUANTITY")
        assertThat(shoppingListItem.sequence).isEqualTo(1)
    }

    private fun shoppingListItem(recipe: Recipe): ShoppingListItem {
        return ShoppingListItem(2, "", "", recipe = recipe)
    }

    private fun updateShoppingListItemRequest(): UpdateShoppingListItemRequest {
        return UpdateShoppingListItemRequest("NAME", "QUANTITY", 1, 3)
    }

    private fun shoppingList(items: MutableSet<ShoppingListItem>): ShoppingList {
        return ShoppingList(
            id = 1,
            user = User(id = 0, email = "", name = ""),
            name = "",
            items = items
        )
    }

    private fun recipe(): Recipe {
        return Recipe(id = 1, name = "", User(email = "", name = ""), photoName = null)
    }

}